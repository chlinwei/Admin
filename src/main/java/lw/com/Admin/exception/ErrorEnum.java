package lw.com.Admin.exception;

import lombok.Getter;

public enum ErrorEnum {
    USER_NOT_EXISTS(1000,"用户不存在"),
    USER_EXISTS(1001,"用户已存在"),
    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或者密码错误"),
    USER_AUTHTICATION_ERROR(1003,"用户验证错误");
    @Getter
    private Integer errCode;
    @Getter
    private String errormsg;
    private ErrorEnum(int errCode,String errormsg) {
        this.errCode = errCode;
        this.errormsg = errormsg;
    }




}
