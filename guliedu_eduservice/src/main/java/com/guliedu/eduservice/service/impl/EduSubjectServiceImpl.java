package com.guliedu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guliedu.eduservice.entity.EduSubject;
import com.guliedu.eduservice.entity.vo.OneSubjectDto;
import com.guliedu.eduservice.entity.vo.TwoSubjectDto;
import com.guliedu.eduservice.handler.EduException;
import com.guliedu.eduservice.mapper.EduSubjectMapper;
import com.guliedu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-16
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {



    @Override
    public List<String> importDataSubject(MultipartFile file) {

        List<String> msg = new ArrayList<>();
        try {
            //1 创建workbook，在workbook 传递文件输入流
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);



            //2、读取sheet
            Sheet sheet = workbook.getSheetAt(0);


            //3、读取row 读取第一行
            //因为行是不确定的， ，表头数据不用遍历 循环遍历
            //从第二行开始读取
            //先得到最后内容所在的行
            /**
             * 索引值的行数
             */
            int lastRowNum = sheet.getLastRowNum();

            /**
             * 实际行数
             */
            // int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();


            for (int i = 1; i <= lastRowNum; i++) {
                //获取每一行数据
                Row row = sheet.getRow(i);

                //读取第一列

                Cell cellOne = row.getCell(0);
                /**
                 * 判断列是否为空
                 */
                 if(cellOne ==null){
                     msg.add("第"+(i+1)+"行数据，第一列的数据为空,其他数据已经添加");
                     continue;


                 }
                //获取第一列的值
                String CellOneValue = cellOne.getStringCellValue();
                // 添加一级分类到数据库表
                /**
                 * 判断列中的值是否为空
                 */
                if(StringUtils.isEmpty(CellOneValue)){
                    msg.add("第"+(i+1)+"行数据，第一列的数据为空,其他数据已经添加");
                    continue;
                }
                String parentId =null;

                EduSubject existSubjectOne = this.SubjectOneIfExist(CellOneValue);
                //如果不存在一级分类则添加一级分类到数据库
                if(existSubjectOne == null){
                    EduSubject subjectOne = new EduSubject();
                    subjectOne.setTitle(CellOneValue);
                    subjectOne.setParentId("0");

                    baseMapper.insert(subjectOne);
                    //添加完一级分类之后，获取添加之后的一级分类id值

                    parentId = subjectOne.getId();
                }else{
                    //如果已经存在一级分类
                    //二级分类的添加要parent id
                    parentId = existSubjectOne.getId();
                }

                //读取第二列
                Cell cellTwo = row.getCell(1);
                if(cellTwo ==null){
                    msg.add("第"+(i+1)+"行数据，第二列的数据为空,其他数据已经添加");
                    continue;
                }
                String CellTwoValue = cellTwo.getStringCellValue();
                if(StringUtils.isEmpty(CellTwoValue)){
                    msg.add("第"+(i+1)+"行数据，第一列的数据为空,其他数据已经添加");
                    continue;
                }

                //判断二级分类是否存在
                EduSubject existSubjectTwo = this.SubjectTwoIfExist(CellTwoValue, parentId);
                if(existSubjectTwo == null) {
                    EduSubject eduSubjectTwo = new EduSubject();
                    eduSubjectTwo.setTitle(CellTwoValue);
                    eduSubjectTwo.setParentId(parentId);

                    baseMapper.insert(eduSubjectTwo);

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return msg;
    }
    //判断二级分类在数据库中是否存在

    public EduSubject SubjectTwoIfExist(String title,String parentId){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",parentId);

        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }
    //判断一级分类在数据库中是否存在

    public EduSubject SubjectOneIfExist(String title){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",0);

        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }
    @Override
    public void addOneSubject(EduSubject eduSubject) {

        //判断一级分类是否存在
        EduSubject eduSubject1 = this.SubjectOneIfExist(eduSubject.getTitle());
        //添加分类
        if(eduSubject1 == null){
            baseMapper.insert(eduSubject);
        }else{
            throw new EduException(20001,"该一级分类已经存在，请重新添加");
        }

    }

    @Override
    public void addTwoSubject(EduSubject eduSubject) {
        EduSubject eduSubject1 = this.SubjectTwoIfExist(eduSubject.getTitle(), eduSubject.getParentId());

        if(eduSubject1 == null){
            baseMapper.insert(eduSubject);

        }else{
            throw new EduException(20001,"二级分类已经存在");
        }


    }

    @Override
    public List<OneSubjectDto> getAllSubjectDto() {
        //查询所有的一级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);

        List<EduSubject> eduSubjectOne = baseMapper.selectList(wrapper);



        //查询所有的二级分类
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();

        wrapper.ne("parent_id",0);

        List<EduSubject> eduSubjectTwo = baseMapper.selectList(wrapper1);

        List<OneSubjectDto> finalDataList = new ArrayList<>();

        for (int i = 0; i < eduSubjectOne.size() ; i++) {
            //得到每个一级分类
            EduSubject eduSubject = eduSubjectOne.get(i);
            OneSubjectDto oneSubjectDto  =  new OneSubjectDto();
            //对考
            BeanUtils.copyProperties(eduSubject,oneSubjectDto);

            finalDataList.add(oneSubjectDto);
            List<TwoSubjectDto> twoSubjectList = new ArrayList<>();

            for (int j = 0; j < eduSubjectTwo.size(); j++) {

                EduSubject eduSubject1 = eduSubjectTwo.get(j);
                if (eduSubject1.getParentId().equals(oneSubjectDto.getId())){
                    TwoSubjectDto twoSubjectDto = new TwoSubjectDto();
                    BeanUtils.copyProperties(eduSubject1,twoSubjectDto);
                    twoSubjectList.add(twoSubjectDto);
                }

            }
            oneSubjectDto.setChildren(twoSubjectList);

        }


        /**
         * 封装一级分类
         * 把一级分类遍历封装
         * 4、封装二级分类
         *
         */




        return finalDataList;
    }

    /**
     * 删除课程分类，目前使用的策略是如果一级分类下有子分类则不删除
     * @param id
     * @return
     */
    @Override
    public boolean deleteSubjectById(String id) {

        QueryWrapper<EduSubject> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer integer = baseMapper.selectCount(queryWrapper);
        if(integer == 0){
            int i = baseMapper.deleteById(id);
            return i>0;
        }else{
            throw new EduException(20001,"此一级分类下存在子分类");

        }


    }
}
