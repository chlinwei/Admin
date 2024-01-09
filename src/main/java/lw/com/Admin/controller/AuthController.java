package lw.com.Admin.controller;


import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.dto.LoginParams;
import lw.com.Admin.domain.vo.TokenVo;
import lw.com.Admin.service.IUserService;
import lw.com.Admin.utils.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("auth")
public class AuthController {

    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 登录方法：返回一个令牌
     * 用户再次访问时，在请求头 header：携带token
     */
    @PostMapping("doLogin")
    @ResponseBody
    public Response login(@RequestBody LoginParams loginParams) {
        log.info("==============================={}",loginParams);
        String token = userService.login(loginParams);
        TokenVo tokenVo =  new TokenVo(token);
        return Response.newResponse_200(tokenVo);
    }
    @GetMapping("login")
    public String getLoginPage() {
        return "login";
    }
    @GetMapping("test")
    @ResponseBody
    public String test() {
        return "test";
    }

}