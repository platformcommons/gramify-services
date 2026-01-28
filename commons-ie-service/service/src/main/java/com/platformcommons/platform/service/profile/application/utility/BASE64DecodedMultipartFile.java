package com.platformcommons.platform.service.profile.application.utility;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.*;

public class BASE64DecodedMultipartFile implements MultipartFile {
    private final byte[] imgContent;

    private final String fileName;

    private final String contentType;

    public BASE64DecodedMultipartFile(byte[] imgContent,String fileName,String contentType) {
        this.imgContent = imgContent;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    @Override
    public @NotNull String getName() {
        return this.fileName;
    }

    @Override
    public String getOriginalFilename() {
        return this.fileName;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte @NotNull [] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public @NotNull InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }
}
