package com.guliedu.eduvod.controller;

import com.gulideu.common.R;

import com.guliedu.eduvod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("/eduvod/vod")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;
    /**
     * 删除多个阿里云中的视频
     *
     */
     @DeleteMapping("deleteMoreAliyunVideo")
     public R deleteMoreAliyunVideo(@PathVariable("videoIdList") List<String> videoIdList){
          vodService.deleteVideoList(videoIdList);
          return R.ok();
     }
    /**
     * 删除阿里云中的视频
     *
     */
    @DeleteMapping("{videoId}")
    public R deleteVideoId(@PathVariable String videoId){
        vodService.deleteAliyunByVideoId(videoId);
        return R.ok();
    }
    /**
     * 上传视频到阿里云视频点播服务
     *
     */

    @PostMapping("uploadVideoAli")
    public R uploadAliyunVideo(MultipartFile file){
        //获取上传文件
        //返回视频id
        String videoid = vodService.uploadVideoAliyun(file);
        return R.ok().data("videoId",videoid);

    }
}
