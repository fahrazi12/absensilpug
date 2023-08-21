package com.ivallavifahrazi.lpugabsensi.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PesertService {

    void importToDb(List<MultipartFile> multipartFileList, Long batchId);

    Integer excelReader(MultipartFile multipartFile);
}
