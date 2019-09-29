package com.guliedu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gulideu.common.R;
import com.guliedu.eduservice.client.VodClient;
import com.guliedu.eduservice.entity.EduVideo;
import com.guliedu.eduservice.mapper.EduVideoMapper;
import com.guliedu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
    @Override
    public void deleteVideoId(String xiaojieId) {
        //删除小节，删除视频
        //根据小节id 查询到视频id
        EduVideo eduVideo = baseMapper.selectById(xiaojieId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.deleteVideoId(videoSourceId);
        }

        baseMapper.deleteById(xiaojieId);


    }

    @Override
    public void deleteVideoById(String id) {
        //根据课程ID 查询课程里面所有视频的ID
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id",id);
        List<EduVideo> videoList = baseMapper.selectList(queryWrapper2);

        List<String> videoIdList = new ArrayList<>();



        vodClient.deleteMoreAliyunVideo();
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        baseMapper.delete(queryWrapper);
    }
}
