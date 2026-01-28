package com.platformcommons.platform.service.profile.facade;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelUploadFacade {
    void uploadIE(MultipartFile file) throws Exception ;

    void uploadIEV2(MultipartFile file) throws Exception ;


    void uploadAsset(MultipartFile file) throws Exception ;

    void uploadPortfolio(MultipartFile file) throws Exception ;

    void uploadABA(MultipartFile file) throws Exception;

    void uploadIEV3(MultipartFile file);
}