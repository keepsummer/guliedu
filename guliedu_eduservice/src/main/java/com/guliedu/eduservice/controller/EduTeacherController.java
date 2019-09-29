package com.guliedu.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulideu.common.R;
import com.guliedu.eduservice.entity.EduTeacher;
import com.guliedu.eduservice.entity.vo.TeacherQuery;
import com.guliedu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lizhimin
 * @since 2019-09-08
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //login
    @GetMapping("login")
    public R login() {
        //{"code":20000,"data":{"token":"admin"}}
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info() {
        //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
    //修改功能
    @PostMapping("UpdateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = teacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        }else{
            return R.error();
        }
    }
    //根据ID进行查询
    @GetMapping("findTeacherInfoId/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("items",eduTeacher);
    }

    //添加功能
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if(save){
            return R.ok();

        }else {
            return R.error();
        }

    }


    //查询所有讲师信息（不带分页）
    @GetMapping("findAllTeacher")
    public R getAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);

    }
    //逻辑删除讲师的方法
    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }


    }

    @GetMapping("findTeacherPage/{page}/{limit}")
    public R getTeacherPage(@PathVariable long page,
                            @PathVariable long limit){

      Page<EduTeacher> pageTeacher = new Page<EduTeacher>(page,limit);

      teacherService.page(pageTeacher,null);

      long total = pageTeacher.getTotal();

        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("rows",records);

    }
    @PostMapping("findTeacherPageCondition/{page}/{limit}")
    public R getTeacherPageCondition(@PathVariable long page,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) TeacherQuery teacherQuery
                                     ){

        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(page,limit);

        teacherService.pageTeacherCondition(pageTeacher, teacherQuery);


        long total = pageTeacher.getTotal();

        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("rows",records);



    }


}

