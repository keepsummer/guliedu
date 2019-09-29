package com.guliedu.eduservice.service;

import com.guliedu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-17
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteVideoId(String videoId);

    void deleteVideoById(String id);
}
