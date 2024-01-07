package lw.com.Admin.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Integer code;
    private String msg;
    private Object data;
    public  static Response newResponse_200(Object data) {
        return new Response(200,"success",data);
    }
    public static Response newAdminExceptionResponse(Integer code,String msg) {
        return new Response(code,msg,null);
    }
    public static Response newInterExceptionResponse(String msg) {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg,null);
    }
}

