package lw.com.Admin.exception;

public class AdminException extends RuntimeException{
    private Integer code;
    public AdminException(ErrorEnum errorEnum) {
        super(errorEnum.getErrormsg());
        this.code = errorEnum.getErrCode();
    }
    public Integer getCode(){
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
}


