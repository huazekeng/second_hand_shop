package com.mrli.second_shop.constant;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(true, 20000,"成功"),
    UNKNOWN_REASON(false, 20001, "未知错误"),
    BAD_SQL_GRAMMAR(false, 21001, "sql语法错误"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
    PARAM_ERROR(false, 21003, "参数不正确"),
    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
    EXCEL_DATA_IMPORT_ERROR(false, 21005, "Excel数据导入错误"),
    USER_NOT_LOGIN(false, 40002, "用户还未登录"),
    LOGIN_ERROR(false, 40003, "登录失败"),
    PERMISSION_DENIED(false,40003,"权限不足"),
    LOGIN_EXPIRES(false,40004,"缺少认证信息或认证信息无效"),
    LOGOUT_SUCCESS(false,40005,"注销成功"),
    USER_NOT_ACTIVE(false,40006,"用戶還未激活"),
    TOKEN_EXPIRE(false, 40007 , "token 過期"),
    REFRESH_TOKEN_EXPIRE(false, 40008, "refresh token過期"),
    REQUIRE_EXCEED(false, 40009, "每个人只能发布5条有效求购，请先下架无效求购");

    private Boolean success;

    private int code;

    private String message;

    private ResultCodeEnum(boolean success,int code ,String message){

        this.success = success;
        this.code = code;
        this.message = message;

    }


}
