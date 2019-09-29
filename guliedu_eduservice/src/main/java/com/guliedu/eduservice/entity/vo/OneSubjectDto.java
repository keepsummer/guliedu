package com.guliedu.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类
@Data
public class OneSubjectDto {
    private String id;

    private String title;

   private List<TwoSubjectDto> children = new ArrayList<>();

}
