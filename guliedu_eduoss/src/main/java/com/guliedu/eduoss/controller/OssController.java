package com.guliedu.eduoss.controller;

import com.gulideu.common.R;
import com.guliedu.eduoss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/oss")
@Api(description="阿里云文件管理")
@CrossOrigin //跨域
public class OssController {


        @Autowired
        private OssService ossService;

        /**
         * 文件上传
         *
         * @param file
         */
        @ApiOperation(value = "文件上传")
        @PostMapping("fileupload")
        public R upload(
                @ApiParam(name = "file", value = "文件", required = true)
                @RequestParam("file") MultipartFile file) {

            String uploadUrl = ossService.uploadFileOss(file);
            //返回r对象
            return R.ok().message("文件上传成功").data("url", uploadUrl);

        }


}
