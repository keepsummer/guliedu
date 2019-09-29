package com.guliedu.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guliedu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guliedu.eduservice.entity.vo.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-08
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void  pageTeacherCondition(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery);
}
