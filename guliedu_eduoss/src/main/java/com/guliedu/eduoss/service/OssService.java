package com.guliedu.eduoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadFileOss(MultipartFile file);
}
