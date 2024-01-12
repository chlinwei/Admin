package lw.com.Admin.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lw.com.Admin.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {

    @Operation(security = {@SecurityRequirement(name="BearerToken")})
    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "hello";
    }
}
