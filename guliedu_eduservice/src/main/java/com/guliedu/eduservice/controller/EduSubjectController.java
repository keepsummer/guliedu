package com.guliedu.eduservice.controller;


import com.gulideu.common.R;
import com.guliedu.eduservice.entity.EduSubject;
import com.guliedu.eduservice.entity.vo.OneSubjectDto;
import com.guliedu.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-16
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    //1、获取Excel上传的文件，poi读取文件内容
    @Autowired
    EduSubjectService subjectService;


    //删除课程分类
    @DeleteMapping("deletesubject/{id}")
    public R deleteSubjectById(@PathVariable String id){
       boolean b = subjectService.deleteSubjectById(id);
       if(b){
          return R.ok();

       }
       return R.error();


    }

    @PostMapping("/import")
    public R importDataSubject(
            @ApiParam(name = "file", value = "Excel文件",required = true)
            @RequestParam MultipartFile file){

        List<String> msg =subjectService.importDataSubject(file);
        if(msg.size()==0){
            return  R.ok();
        }

        return  R.error().data("msg",msg);

    }
    //获取所有分类信息
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        //OneSubjectDto 里面有一级分类，也有二级分类
        List<OneSubjectDto> list = subjectService.getAllSubjectDto();
        return R.ok().data("list",list);
    }
    //添加一级分类
    @PostMapping("addOneSubject")
    public R addOneSubject(@RequestBody EduSubject eduSubject){
        subjectService.addOneSubject(eduSubject);
        return R.ok();
    }
    //添加二级分类
    @PostMapping("addTwoSubject")
    public R addTwoSubject(@RequestBody EduSubject eduSubject){
        subjectService.addTwoSubject(eduSubject);
        return R.ok();
    }

}

