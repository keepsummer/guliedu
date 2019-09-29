package com.guliedu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guliedu.eduservice.entity.EduChapter;
import com.guliedu.eduservice.entity.EduVideo;
import com.guliedu.eduservice.entity.vo.ChapterVo;
import com.guliedu.eduservice.entity.vo.VideoVo;
import com.guliedu.eduservice.handler.EduException;
import com.guliedu.eduservice.mapper.EduChapterMapper;
import com.guliedu.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guliedu.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    @Override
    public void addChapter(EduChapter eduChapter) {
        baseMapper.insert(eduChapter);
    }

    @Override
    public List<ChapterVo> getChapterByCourseId(String courseId) {
       //根据课程ID查询出所有的章节list
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(queryWrapper);
        //2 根据课程id查询课程里面所有小节
        QueryWrapper<EduVideo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduVideo> eduvideolist = videoService.list(queryWrapper1);
        //数据封装
        List<ChapterVo> chapterVoList = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            List<VideoVo> videoVoList =new ArrayList<>();
            for (int y = 0; y < eduvideolist.size(); y++) {

                EduVideo eduVideo = eduvideolist.get(y);
                if(eduVideo.getChapterId().equals(eduChapter.getId())){

                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);

                }
                chapterVo.setChildren(videoVoList);

            }

            chapterVoList.add(chapterVo);


        }
        return chapterVoList;
    }

    @Override
    public void deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if(count ==0) {//没有小节
            //删除章节
            baseMapper.deleteById(chapterId);
        } else {
            throw new EduException(20001,"不能删除");
        }


    }

    @Override
    public void deleteChapterById(String id) {
        QueryWrapper<EduChapter> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id",id);
        baseMapper.delete(queryWrapper1);
    }


}
