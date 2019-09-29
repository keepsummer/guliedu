package com.guliedu.eduservice.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //无参数构造
@AllArgsConstructor  //有参数构造
public class EduException extends RuntimeException{

    private Integer code;

    private String msg;

}
