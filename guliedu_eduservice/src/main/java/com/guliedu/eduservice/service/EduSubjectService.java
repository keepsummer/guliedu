package com.guliedu.eduservice.service;

import com.guliedu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guliedu.eduservice.entity.vo.OneSubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-16
 */
public interface EduSubjectService extends IService<EduSubject> {


   

    List<String> importDataSubject(MultipartFile file);

    void addOneSubject(EduSubject eduSubject);

    void addTwoSubject(EduSubject eduSubject);

    List<OneSubjectDto> getAllSubjectDto();

    boolean deleteSubjectById(String id);
}
