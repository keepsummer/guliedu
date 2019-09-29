package com.guliedu.eduservice.service;

import com.guliedu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guliedu.eduservice.entity.vo.CourseInfoConfirmVo;
import com.guliedu.eduservice.entity.vo.CourseInfoForm;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-17
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseService(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String id);

    void updateCourseInfo(CourseInfoForm courseInfoForm);
    /**
     * 根据课程ID 查询课程信息
     * 返回的是发布课程第三步的数据
     */
    CourseInfoConfirmVo getCourseInfoConfirm(String courseId);

    boolean deleteCourseById(String id);
}
