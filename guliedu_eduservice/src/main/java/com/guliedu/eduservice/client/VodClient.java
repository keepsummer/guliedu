package com.guliedu.eduservice.client;

import com.gulideu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("guliedu-eduvod")
@Component
public interface VodClient {
    //删除视频

    @DeleteMapping("/eduvod/vod/{videoId}")
    public R deleteVideoId(@PathVariable("videoId") String videoId);

    @DeleteMapping("/eduvod/vod/deleteMoreAliyunVideo")
    public R deleteMoreAliyunVideo(@PathVariable("videoIdList") List<String> videoIdList);
}
