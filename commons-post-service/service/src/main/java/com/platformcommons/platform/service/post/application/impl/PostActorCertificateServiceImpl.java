package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.post.application.PostActorCertificateService;
import com.platformcommons.platform.service.post.application.PostService;
import com.platformcommons.platform.service.post.application.TenantCertificateTemplateService;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.application.utility.DateTimeUtil;
import com.platformcommons.platform.service.post.application.utility.NotificationUtility;
import com.platformcommons.platform.service.post.domain.*;
import com.platformcommons.platform.service.post.domain.repo.PostActorCertificateRepository;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostActorCertificateServiceImpl implements PostActorCertificateService {

    @Autowired
    private PostActorCertificateRepository repository;

    @Autowired
    private NotificationUtility notificationUtility;

    @Autowired
    private TenantCertificateTemplateService tenantCertificateTemplateService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private DateTimeUtil dateTimeUtil;

    @Autowired
    private PostService postService;

    @Override
    public PostActorCertificate saveOrUpdate(PostActorCertificate postActorCertificate, PostActor postActor,
                                             Post post, Boolean sendCertificateMail) {
        PostActorCertificate  finalPostActorCertificate;
        Optional<PostActorCertificate> postActorCertificateOptional = repository.findById(postActorCertificate.getId());
        if(postActorCertificateOptional.isPresent()) {
            finalPostActorCertificate = postActorCertificateOptional.get();
            finalPostActorCertificate.patchUpdate(postActorCertificate);
        }
        else {
            finalPostActorCertificate = postActorCertificate;
            finalPostActorCertificate.init(post,postActor);
        }
        return repository.save(finalPostActorCertificate);
    }

    @Override
    public PostActorCertificate save(PostActorCertificate postActorCertificate) {
        return repository.save(postActorCertificate);
    }

    @Override
    public void saveAll(List<PostActorCertificate> postActorCertificates) {
        repository.saveAll(postActorCertificates);
    }
    @Override
    public PostActorCertificate save(PostActorCertificate postActorCertificate, Long postId, Boolean sendCertificateMail) {
        Optional<PostActorCertificate> postActorCertificateOptional = getByPostIdAndTemplateCode(postId,postActorCertificate.getCertificateTemplateCode());
        if(postActorCertificateOptional.isPresent()) {
            throw new InvalidInputException(String.format("PostActorCertificate with template code - %s for post id - %d already exists",
                    postActorCertificate.getCertificateTemplateCode(),postId));
        }
        else {
            Post post = postService.getById(postId);
            PostActor postActor = post.getPostedBy();
            postActorCertificate.init(post,postActor);

            if(Objects.equals(sendCertificateMail,Boolean.TRUE)) {
                notificationUtility.sendPostMail(postActorCertificate,post,postActor);
                postActorCertificate.updateFieldsForMailSent();
            }
            return repository.save(postActorCertificate);
        }

    }

    public Optional<PostActorCertificate> getByPostIdAndTemplateCode(Long postId,String templateCode) {
        return repository.findByPostIdAndTemplateCode(postId,templateCode).stream().findFirst();
    }

    @Override
    public Map<Long, Boolean> getExistenceByPostIds(Set<Long> postIds) {
        Set<Long> certificateExistsPostIds = repository.findExistenceByPostIds(postIds);
        return postIds.stream()
                .collect(Collectors.toMap(Function.identity(), certificateExistsPostIds::contains,(a, b)->b));
    }

    @Override
    public PostActorCertificate getByPostId(Long postId) {
        return repository.findByPostId(postId).stream().findFirst().orElse(null);
    }

    @Override
    public List<PostActorCertificate> createCertificateInBulk(List<Post> PostSet, Long tenantCertificateTemplateId) {
        if(tenantCertificateTemplateId == null) {
            throw new InvalidInputException("Tenant Certificate Template Id must not be null");
        }
        TenantCertificateTemplate tenantCertificateTemplate = tenantCertificateTemplateService.getById(tenantCertificateTemplateId);
        Set<CertificateTextDetails> certificateTextDetailsSet = tenantCertificateTemplate.getCertificateTextDetailsSet();
        ImagePlus templateImage = new ImagePlus(tenantCertificateTemplate.getTemplateUrl());
        Set<PostActorCertificate> postActorCertificateSet = new HashSet<>();

        for(Post post : PostSet) {
            ImagePlus certificateImage = new ImagePlus("Certificate",templateImage.getProcessor().duplicate());
            ImageProcessor ip = certificateImage.getProcessor();
            appendTextOverCertificateTemplate(certificateTextDetailsSet,post,ip,tenantCertificateTemplate);
            BufferedImage bufferedImage = ip.getBufferedImage();
            MultipartFile certificateFile;
            String fileName = createMockFileName(post,tenantCertificateTemplate);
            certificateFile = convertToMultipartFile(bufferedImage,fileName);
            Attachment savedAttachment = createAttachment(post.getPostedBy().getId(), PostConstant.ENTITY_TYPE_POST_ACTOR_CERTIFICATE,certificateFile);
            PostActorCertificate postActorCertificate = createPostActorCertificate(post,savedAttachment,tenantCertificateTemplate);
            postActorCertificateSet.add(postActorCertificate);
        }
        templateImage.close();
        return repository.saveAll(postActorCertificateSet);
    }

    public void appendTextOverCertificateTemplate(Set<CertificateTextDetails> certificateTextDetailsSet, Post post,
                                                  ImageProcessor ip, TenantCertificateTemplate tenantCertificateTemplate) {
        for(CertificateTextDetails certificateTextDetails : certificateTextDetailsSet) {
            String text = null;
            if(Objects.equals(certificateTextDetails.getTextCode(),PostConstant.CERTIFICATE_TEXT_CODE_USER_NAME)) {
                text = post.getPostedBy().getName().toUpperCase();
            }
            else if(Objects.equals(certificateTextDetails.getTextCode(),PostConstant.CERTIFICATE_TEXT_CODE_EFFORT)) {
                text = String.valueOf(Math.round(post.getApprovedEffort().getValue()));
            }
            else if(Objects.equals(certificateTextDetails.getTextCode(),PostConstant.CERTIFICATE_TEXT_CODE_BENEFICIARY)) {
                PostImpact postImpact = post.getPostImpacts().stream().findFirst().orElseThrow(
                        ()->new NotFoundException(String.format("Post Impact not present in post with id %d",post.getId())));
                text = String.valueOf(Math.round(postImpact.getApprovedImpact().getValue()));
            }
            else if(Objects.equals(certificateTextDetails.getTextCode(),PostConstant.CERTIFICATE_TEXT_CODE_UDIN)) {
                text = tenantCertificateTemplate.getCertificateUUIDFormat().concat(String.valueOf(post.getId()));
            }
            else if(Objects.equals(certificateTextDetails.getTextCode(),PostConstant.CERTIFICATE_TEXT_CODE_ISSUED_DATE)) {
                text = dateTimeUtil.convertLocalDateToGivenStringFormat(LocalDate.now(),"dd-MMM-yyyy").toUpperCase();
            }
            Font font = new Font(certificateTextDetails.getFontName(), certificateTextDetails.getFontStyle(),
                    certificateTextDetails.getFontSize());
            int x;
            int y = certificateTextDetails.getPointY();
            Color textColor = Color.decode(certificateTextDetails.getColorCode());
            ip.setColor(textColor);
            ip.setFont(font);
            x = findXValueBasedOnJustification(text,font,certificateTextDetails.getJustification(),certificateTextDetails.getPointX());
            ip.drawString(text, x, y);

        }
    }

    public PostActorCertificate createPostActorCertificate(Post post,Attachment attachment,TenantCertificateTemplate tenantCertificateTemplate) {
        PostActorCertificate postActorCertificate =  new PostActorCertificate();
        postActorCertificate.init(post,post.getPostedBy());
        postActorCertificate.setUrl(attachment.getCompleteURL());
        postActorCertificate.setCertificateTemplateCode(tenantCertificateTemplate.getCode());
        postActorCertificate.setTitle(tenantCertificateTemplate.getTitle());
        return postActorCertificate;
    }

    public String createMockFileName(Post post, TenantCertificateTemplate tenantCertificateTemplate) {
        String name = "";
        if(!StringUtils.isBlank(post.getPostedBy().getName())) {
            name = post.getPostedBy().getName().toUpperCase().replace(" ","_");
        }
        if(!StringUtils.isBlank(tenantCertificateTemplate.getTitle())) {
            if(!StringUtils.isBlank(name)) {
                name = name.concat("_");
            }
            name = name.concat(tenantCertificateTemplate.getTitle().toUpperCase().replace(" ","_"));
        }
        return name;
    }

    public int findXValueBasedOnJustification(String text, Font font, Integer justification, Integer xPoint) {
        int x = xPoint;
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D bounds = font.getStringBounds(text, frc);
        int textWidth = (int) bounds.getWidth();
        if(justification == null) {
            justification = 0;
        }
        if(justification.equals(0)) {
           x =  xPoint - (textWidth/2);
        }
        else if(justification.equals(-1)) {
            x =  xPoint;
        }
        else if(justification.equals(1)) {
            x =  xPoint + (textWidth/2);
        }
        return x;
    }


    public MultipartFile convertToMultipartFile(BufferedImage image, String fileName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            throw new InvalidInputException("Could not convert buffered image into byte array stream");
        }

        return new MockMultipartFile(
                fileName,
                fileName,
                MediaType.IMAGE_JPEG_VALUE,
                outputStream.toByteArray()
        );
    }

    public Attachment createAttachment(Long entityId,String entityType, MultipartFile file) {
        Attachment attachment = new Attachment();
        attachment.setEntityId(entityId);
        attachment.setEntityType(entityType);
        attachment.setPublic(Boolean.TRUE);
        try {
            return attachmentService.uploadAttachment(file,attachment);
        } catch (IOException e) {
            throw new InvalidInputException("File could not be uploaded");
        }
    }

    public PostActorCertificate getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("PostActorCertificate with Id %d not found",id)));
    }
}
