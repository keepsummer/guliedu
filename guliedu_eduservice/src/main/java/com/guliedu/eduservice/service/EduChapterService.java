package com.guliedu.eduservice.service;

import com.guliedu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guliedu.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-17
 */
public interface EduChapterService extends IService<EduChapter> {

    void addChapter(EduChapter eduChapter);

    List<ChapterVo> getChapterByCourseId(String courseId);

    void deleteChapter(String chapterId);

    void deleteChapterById(String id);
}
