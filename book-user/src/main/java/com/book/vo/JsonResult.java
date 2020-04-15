package com.book.vo;

import lombok.Data;

@Data
public class JsonResult {
    //状态码
    private int state=1;//1表示SUCCESS,0表示ERROR
    //状态信息
    private String message="ok";
    //正确数据
    private Object data;

    public JsonResult(){
    }
    public JsonResult(String message){
        this.message=message;
    }
    public JsonResult(Object data){ this.data=data; }
    public JsonResult(Throwable e){
        this.message=e.getMessage();
        this.state=0;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
