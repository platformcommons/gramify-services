package com.platformcommons.platform.service.report.application.constant;

public enum FileType {

    EXCEL("XLSX",".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    CSV("CSV",".csv","text/csv");

    private final String fileType;
    private final String extension;
    private final String contentType;

    FileType(String fileType, String extension,String contentType) {
        this.fileType = fileType;
        this.extension = extension;
        this.contentType= contentType;
    }

    public String getFileType() {
        return fileType;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }
}
