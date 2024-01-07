package lw.com.Admin.exception;

import lw.com.Admin.utils.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        if (e instanceof AdminException) {
            var adminException = (AdminException)e;
            return Response.newAdminExceptionResponse(adminException.getCode(),adminException.getMessage());
        }else {
            return Response.newInterExceptionResponse(e.getMessage());
        }
    }
}
