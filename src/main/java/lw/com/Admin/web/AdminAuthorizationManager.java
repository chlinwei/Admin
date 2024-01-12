package lw.com.Admin.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.entity.Menu;
import lw.com.Admin.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * 判断请求路径是否有权限
 */
@Component
@Slf4j
@ConfigurationProperties(prefix="admin-authorization-manager")
public class AdminAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    public void setWhileList(String[] whileList) {
        this.whileList = whileList;
    }

    public String[] getWhileList() {
        return whileList;
    }

    private String[] whileList;
    @Autowired
    private  final MenuMapper menuMapper;
    public AdminAuthorizationManager(MenuMapper menuMapper,String[] whileList) {
        this.whileList = whileList;
        this.menuMapper = menuMapper;
    }
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        //获取请求路径
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        //方行
        if("/auth/login".equals(uri) || "/logout".equals(uri) || "/error".equals(uri)) {
            return new AuthorizationDecision(true);
        }
        //白名单
        for(String item :this.whileList) {
            if(uri.replaceFirst("/","").startsWith(item)) {
                return new AuthorizationDecision(true);
            }
        }
        log.info("whileList:{}",this.whileList);
        //根据uri获取路径的访问权限
        Menu menu = menuMapper.selectOne(new LambdaQueryWrapper<Menu>().eq(Menu::getPath, uri.replaceFirst("/","")));
        if(menu == null) {
            return new AuthorizationDecision(false);
        }
        //获取路径访问权限
        String perm = menu.getPerms();
        log.info("路径权限：{}",perm);
        if(perm == null || perm.trim().equals("")) {
            return new AuthorizationDecision(true);
        }

        //获取用户的权限
        Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
        for(GrantedAuthority authority : authorities ) {
            String userPerm = authority.getAuthority();
            log.info("用户权限：{}",userPerm);
            if(userPerm.equals(perm)) {
                return new AuthorizationDecision(true);
            }
        }
        return new AuthorizationDecision(false);
    }
}
