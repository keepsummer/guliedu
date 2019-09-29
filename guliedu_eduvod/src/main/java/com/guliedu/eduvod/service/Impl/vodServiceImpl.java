package com.guliedu.eduvod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.guliedu.eduservice.handler.EduException;
import com.guliedu.eduvod.service.VodService;
import com.guliedu.eduvod.utils.AliyunVodSDKUtils;
import com.guliedu.eduvod.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class vodServiceImpl implements VodService {
    @Override
    public String uploadVideoAliyun(MultipartFile file) {
        try {
            //文件名称
            String fileName = file.getOriginalFilename();
            //不带后缀名值
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //通过response获取上传视频id
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteAliyunByVideoId(String videoId) {
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();

            request.setVideoIds(videoId);

            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        }catch (ClientException e){
            throw new EduException(20001, "视频删除失败");
        }
    }

    @Override
    public void deleteVideoList(List<String> videoIdList) {

        try{
            //初始化对象
            DefaultAcsClient client=
                    AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建一个request 对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            String videoIds = StringUtils.join(videoIdList.toArray(),",");
            request.setVideoIds(videoIds);

            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
            }catch (Exception e){
            e.printStackTrace();
        }

    }
}
