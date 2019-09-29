package com.guliedu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guliedu.eduservice.entity.EduTeacher;
import com.guliedu.eduservice.entity.vo.TeacherQuery;
import com.guliedu.eduservice.mapper.EduTeacherMapper;
import com.guliedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.swing.StringUIClientPropertyKey;

import javax.management.Query;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-08
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageTeacherCondition(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery) {
    //1、判断条件是否为空，如果不为空拼接条件
        if (teacherQuery == null){
            //查询所有
            baseMapper.selectPage(pageTeacher,null);
            return;
        }
        //2 把条件对象里面值获取出来，再进行判断
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空，拼接条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(name)) {//name不为kong
            //拼接条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);

        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);

        }
        if(!StringUtils.isEmpty(end)){
            wrapper.ge("gmt_create",end);

        }
        //3 调用方法实现条件查询带分页
        baseMapper.selectPage(pageTeacher,wrapper);

    }
}
