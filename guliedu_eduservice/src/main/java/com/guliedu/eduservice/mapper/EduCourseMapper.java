package com.guliedu.eduservice.mapper;

import com.guliedu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guliedu.eduservice.entity.vo.CourseInfoConfirmVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程信息
    CourseInfoConfirmVo getCourseInfoConfirm(String courseId);

}
