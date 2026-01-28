package com.platformcommons.platform.service.profile.application.utility;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;

@Component
public class MultipleIeCardUtil {

    public byte[] mergePdfsUsingUtility(List<byte[]> pdfList) throws IOException {
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        pdfList.stream()
                .filter(pdfBytes -> pdfBytes != null && pdfBytes.length > 0)
                .forEach(pdfBytes -> pdfMerger.addSource(new ByteArrayInputStream(pdfBytes)));

        pdfMerger.setDestinationStream(outputStream);
        pdfMerger.mergeDocuments(null);
        return outputStream.toByteArray();
    }

    public byte[] getPdfBytesFromPath(String path) {
        try {
            if (path.startsWith("http://") || path.startsWith("https://")) {
                return downloadPdfFromUrl(path);
            } else {
                return decodePdfStringToBytes(path);//need to use
            }
        } catch (Exception e) {
            System.err.println("Failed to retrieve PDF from path: " + path);
            e.printStackTrace();
            return null;
        }
    }

    private byte[] downloadPdfFromUrl(String pdfUrl) {
        try {
            URL url = new URL(pdfUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }
                    return byteArrayOutputStream.toByteArray();
                }
            } else {
                System.err.println("Failed to download PDF from URL: " + pdfUrl +
                        " with response code: " + connection.getResponseCode());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Exception occurred while downloading PDF from URL: " + pdfUrl);
            e.printStackTrace();
            return null;
        }
    }
    private byte[] decodePdfStringToBytes(String encodedPdf) {
        try {
            return Base64.getDecoder().decode(encodedPdf);
        } catch (IllegalArgumentException e) {
            System.err.println("Base64 decoding failed for: " + encodedPdf);
            e.printStackTrace();
            return null;
        }
    }

}
