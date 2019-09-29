package com.guliedu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guliedu.eduservice.entity.EduChapter;
import com.guliedu.eduservice.entity.EduCourse;
import com.guliedu.eduservice.entity.EduCourseDescription;
import com.guliedu.eduservice.entity.EduVideo;
import com.guliedu.eduservice.entity.vo.CourseInfoConfirmVo;
import com.guliedu.eduservice.entity.vo.CourseInfoForm;
import com.guliedu.eduservice.handler.EduException;
import com.guliedu.eduservice.mapper.EduCourseMapper;
import com.guliedu.eduservice.service.EduChapterService;
import com.guliedu.eduservice.service.EduCourseDescriptionService;
import com.guliedu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guliedu.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;


    @Override
    public String addCourseService(CourseInfoForm courseInfoForm) {

        /**
         * 1、把课程基本信息添加到课程表
         * 2、把课程简介添加到课程描述表
         */


        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        int i = baseMapper.insert(eduCourse);
        //判断是否添加成功，失败则抛出异常
        if (i == 0) {
            throw new EduException(20001, "添加失败");
        }
        //获取课程id
        String courseId = eduCourse.getId();


        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescription.setId(courseId);
        eduCourseDescriptionService.save(eduCourseDescription);

        return courseId;


    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        CourseInfoForm courseInfoForm = new CourseInfoForm();

        //查询基础信息
        EduCourse eduCourse = baseMapper.selectById(id);
        BeanUtils.copyProperties(eduCourse, courseInfoForm);
        //查询描述
        EduCourseDescription desc = eduCourseDescriptionService.getById(id);
        BeanUtils.copyProperties(desc, courseInfoForm);
        //
        return courseInfoForm;

    }

    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        //修改基础数据
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        baseMapper.updateById(eduCourse);


        //修改描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoForm.getId());
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);

    }

    @Override
    public CourseInfoConfirmVo getCourseInfoConfirm(String courseId) {
        CourseInfoConfirmVo courseInfoConfirm = baseMapper.getCourseInfoConfirm(courseId);

        return courseInfoConfirm;
    }

    @Override
    public boolean deleteCourseById(String id) {
        //删除小节

        eduVideoService.deleteVideoById(id);
        //删除章节
        eduChapterService.deleteChapterById(id);

        //删除描述
        eduCourseDescriptionService.removeById(id);

        //删除基本信息
        baseMapper.deleteById(id);

        return true;
    }

}
