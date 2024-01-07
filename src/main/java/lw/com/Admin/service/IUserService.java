package lw.com.Admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import lw.com.Admin.domain.dto.LoginParams;
import lw.com.Admin.domain.entity.Menu;
import lw.com.Admin.domain.entity.User;

public interface IUserService extends IService<User> {
    String login(LoginParams loginParams);
}