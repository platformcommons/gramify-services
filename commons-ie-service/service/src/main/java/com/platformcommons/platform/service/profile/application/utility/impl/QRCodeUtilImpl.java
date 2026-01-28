package com.platformcommons.platform.service.profile.application.utility.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.profile.application.utility.BASE64DecodedMultipartFile;
import com.platformcommons.platform.service.profile.application.utility.QRCodeUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class QRCodeUtilImpl implements QRCodeUtil {


    @Autowired
    private AttachmentService attachmentService;


    public static final String IMAGE_FORMAT_PNG = "PNG";
    public static final String IMAGE_FORMAT_PNG_EXTENTION = ".png";
    public static final String IMAGE_FORMAT_PNG_MIME = "image/x-png";

    @Override
    public String getQRCodeLink(String data, Long entityId, String entityType,
                                String attachmentKind, String attachmentKindIdentifier, Boolean isPublic) {
        try {
            byte[] qrData = getQRBinaryData(data, 512, 512, IMAGE_FORMAT_PNG);
            MultipartFile multipartFile = new BASE64DecodedMultipartFile(qrData, entityId + IMAGE_FORMAT_PNG_EXTENTION,
                    IMAGE_FORMAT_PNG_MIME);

            Attachment attachment = attachmentBuilder(entityId, entityType, attachmentKind, attachmentKindIdentifier, null, isPublic);
            Attachment savedAttachment=attachmentService.uploadAttachment(multipartFile, attachment);
            return savedAttachment.getCompleteURL();
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to upload QR code \n"+ExceptionUtils.getMessage(e));
        }
    }


    private Attachment attachmentBuilder(Long entityId, String entityType, String attachmentKind,
                                         String attachmentKindIdentifier, String attachmentKindMeta, Boolean isPublic) {
        Attachment attachment = new Attachment();
        attachment.setEntityId(entityId);
        attachment.setEntityType(entityType);
        attachment.setAttachmentKind(attachmentKind);
        attachment.setAttachmentKindIdentifier(attachmentKindIdentifier);
        attachment.setAttachmentKindMeta(attachmentKindMeta);
        attachment.setPublic(isPublic);
        return attachment;
    }


    private byte[] getQRBinaryData(String data,int width, int height, String imageFormat){
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(data),
                    BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix,imageFormat,outputStream);
            return  outputStream.toByteArray();
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Unable to generate QR Code \n"+ ExceptionUtils.getMessage(e));
        }
    }
}
