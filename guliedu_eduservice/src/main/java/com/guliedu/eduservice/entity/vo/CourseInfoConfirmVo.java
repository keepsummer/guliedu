package com.guliedu.eduservice.entity.vo;

import lombok.Data;

@Data
public class CourseInfoConfirmVo {
    private String id;//课程id
    private String title;//课程名称
    private String cover;//封面
    private Integer lessonNum;
    private String subjectLevelOne; //一级分类名称
    private String subjectLevelTwo; //二级分类名称
    private String teacherName; //讲师名称
    private String price;//课程价格
}
