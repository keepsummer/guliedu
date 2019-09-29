package com.guliedu.eduservice.handler;

import com.gulideu.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    //处理全局异常的方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常....");
    }

    //处理特殊异常的方法
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了特殊异常处理....");
    }

    //处理自定义异常的方法
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e) {
        //EduException e1 = new EduException(20001,"失败");
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}

