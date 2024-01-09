package lw.com.Admin.web.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.entity.User;
import lw.com.Admin.exception.AdminException;
import lw.com.Admin.exception.ErrorEnum;
import lw.com.Admin.web.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 捕获请求中的请求头，获取token字段，判断是否可以获取用户信息
 * 我们可以继承 OncePerRequestFilter 抽象类
 *
 * 1、获取到用户信息之后，需要将用户的信息告知SpringSecurity，SpringSecurity会去判断你访问的接口是否有相应的权限
 * 2、告知SpringSecurity 就是使用Authentication告知框架，SpringSecurity、会将信息存储到SecurityContext中-----》SecurityContextHolder中
 *
 * 登录的时候，放置的数据是用户名和密码。是要查找用的
 * 后边请求，判断权限的时候，放置进去的数据是用户的信息。密码就不需要了，还有用户的权限
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 该方法会被doFilter调用
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        // 获取token
        String token = request.getHeader("Authorization");
        log.info("==============================={}",token);
        // login请求就没token，直接放行，因为后边有其他的过滤器
        if(token == null) {
            doFilter(request,response,filterChain);
            return;
        }
        // 有token，通过jwt工具类，解析用户信息
        Claims claims = null;
        try {
            claims = jwtUtils.parseToken(token);
        }catch (SignatureException e){
            // 需要返回401，重新登陆,由于在filter里不能直接抛异常
            resolver.resolveException(request,response,null,new AdminException(ErrorEnum.USER_SIGNATURE_ERROR));
            return;
        }catch (ExpiredJwtException e) {
            resolver.resolveException(request,response,null,new AdminException(ErrorEnum.USER_TOKEN_EXPIRED_ERROR));
            return;
        }
        catch (Exception e) {
            resolver.resolveException(request,response,null,new AdminException(ErrorEnum.USER_TOKEN_FORMAT_ERROR));
            return;
        }
        log.info("claims======>{}",claims);
        // 获取到了数据，将数据取出，放到UmsSysUser中
        Long id = claims.get("id", Long.class);
        String username = claims.get("username", String.class);
        String avatar = claims.get("avatar", String.class);
        List<String> perms = claims.get("perms", ArrayList.class);
        // 将信息放到User类中
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setAvatar(avatar);
        user.setPerms(perms);
        log.info("user======>{}", user);
        // 将用户信息放到SecurityContext中
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 放行
        doFilter(request,response,filterChain);
    }
}