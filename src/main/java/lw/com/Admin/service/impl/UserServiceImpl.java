package lw.com.Admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.dto.LoginParams;
import lw.com.Admin.domain.entity.User;
import lw.com.Admin.exception.AdminException;
import lw.com.Admin.exception.ErrorEnum;
import lw.com.Admin.mapper.UserMapper;
import lw.com.Admin.service.IUserService;
import lw.com.Admin.web.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService  {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 间接调用 loadUserByUsername方法
     * @param loginParams
     * @return
     */
    @Override
    public String login(LoginParams loginParams) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginParams.getUsername(), loginParams.getPassword());
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e) {
            throw new AdminException(ErrorEnum.USERNAME_OR_PASSWORD_ERROR);
        } catch (AuthenticationException e) {
            throw new AdminException(ErrorEnum.USER_AUTHTICATION_ERROR);
        }
        // 就获取用户信息，返回token
        User user = (User) authentication.getPrincipal();
        if(user == null) {
            throw new AdminException(ErrorEnum.USER_AUTHTICATION_ERROR);
        }
        // 将用户信息通过JWT生成token，返回给前端
        Map<String, Object> map = new HashMap<>();
        map.put("id",user.getId());
        map.put("username",user.getUsername());
        map.put("avatar",user.getAvatar());
        map.put("perms",user.getPerms());
        return jwtUtils.createToken(map);
    }
}