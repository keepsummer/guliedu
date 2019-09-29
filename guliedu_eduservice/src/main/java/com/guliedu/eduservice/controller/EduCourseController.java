package com.guliedu.eduservice.controller;


import com.gulideu.common.R;
import com.guliedu.eduservice.entity.EduCourse;
import com.guliedu.eduservice.entity.vo.CourseInfoConfirmVo;
import com.guliedu.eduservice.entity.vo.CourseInfoForm;
import com.guliedu.eduservice.service.EduCourseDescriptionService;
import com.guliedu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-17
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 实现课程信息修改接口
     */
    @PostMapping("updateCourse")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        eduCourseService.updateCourseInfo(courseInfoForm);

        return R.ok();
    }
    /**
     * 添加课程的基本信息
     */
    @PostMapping("addCourseInfo")
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
     String courseId = eduCourseService.addCourseService(courseInfoForm);

        return R.ok().data("courseId",courseId);
    }

    /**
     * 根据课程id查询信息
     */
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfoById(@PathVariable String id){
      CourseInfoForm courseInfoForm = eduCourseService.getCourseInfoById(id);
      return R.ok().data("courseInfo",courseInfoForm);
    }
    /**
     * 根据课程id查询课程发布的第三步数据
     *
     */
    @GetMapping("getCourseInfoConfirm/{courseId}")
    public R getCourseInfoConfirm(@PathVariable String courseId){
        CourseInfoConfirmVo courseInfoConfirm = eduCourseService.getCourseInfoConfirm(courseId);
        return R.ok().data("courseInfoConfirm",courseInfoConfirm);

    }
    /**
     *课程的最终发布
     */
    //课程发布的方法
    @PutMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }
    /**
     * 获取课程列表
     */
    @GetMapping("getAllCourse")
    public R getAllCourse(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("items",list);

    }
    /**
     * 删除课程
     *
     */
    @DeleteMapping("deleteCourse/{id}")
    public R deleteCourse(@PathVariable String id){

        boolean b = eduCourseService.deleteCourseById(id);
        if (b){
            return R.ok();
        }else{
            return R.error();
        }


    }
}

