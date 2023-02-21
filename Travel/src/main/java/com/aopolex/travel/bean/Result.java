package com.aopolex.travel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

//封装返回的数据
@Data
@AllArgsConstructor
public class Result {
    private boolean flag;   //返回状态
    private String message; //反馈信息
    private Object date;    //返回数据

    public Result(boolean flag,String message){
        this(flag,message,null);
    }
}
