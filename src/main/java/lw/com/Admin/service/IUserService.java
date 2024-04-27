package lw.com.Admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import lw.com.Admin.domain.dto.LoginParams;
import lw.com.Admin.domain.entity.User;
import org.springframework.stereotype.Service;

public interface IUserService extends IService<User>{
    String login(LoginParams loginParams);
}