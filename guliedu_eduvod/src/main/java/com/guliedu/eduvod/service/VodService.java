package com.guliedu.eduvod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideoAliyun(MultipartFile file);

    void deleteAliyunByVideoId(String videoId);

    void deleteVideoList(List<String> videoList);
}
