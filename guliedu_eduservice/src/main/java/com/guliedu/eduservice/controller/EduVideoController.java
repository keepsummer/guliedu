package com.guliedu.eduservice.controller;


import com.gulideu.common.R;
import com.guliedu.eduservice.entity.EduVideo;
import com.guliedu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-17
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {


    @Autowired
    private EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //根据id查询小节的方法
    @GetMapping("getVideoInfoId/{videoId}")
    public R getVideoInfoId(@PathVariable String videoId) {
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

    //删除小节
    @DeleteMapping("{videoId}")
    public R removeVideo(@PathVariable String videoId) {
        videoService.deleteVideoId(videoId);
        return R.ok();
    }

}

