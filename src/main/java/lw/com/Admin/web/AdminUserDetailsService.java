package lw.com.Admin.web;

import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.entity.Menu;
import lw.com.Admin.domain.entity.Role;
import lw.com.Admin.domain.entity.User;
import lw.com.Admin.mapper.MenuMapper;
import lw.com.Admin.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AdminUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;
    private final MenuMapper menuMapper;

    public AdminUserDetailsService(UserMapper sysUserMapper, MenuMapper menuMapper) {
        this.userMapper = sysUserMapper;
        this.menuMapper = menuMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername");

        // 做用户信息查询，不要多次访问数据库，尽量一次查出需要的数据，多表查询不要超过3张表
        // 1、查询用户的角色信息
        User user = userMapper.selectUserByUsername(username);
        // 2、查询用户的权限信息
        if(user != null) {
            List<Role> roleSet =  user.getRoleSet();
            // 存储角色id，进行批量查询，不要在for循环中查询数据库
            Set<Long> roleIds = new HashSet<>(roleSet.size());
            // 获取用户的权限列表
            List<String> perms = user.getPerms();
            for (Role umsRole : roleSet) {
                roleIds.add(umsRole.getRoleId());
            }
            // 权限查询
            Set<Menu> menus = menuMapper.selectMenuByRoleId(roleIds);
            for (Menu menu : menus) {
                String perm = menu.getPerms();
                // 添加用户权限到set中
                perms.add(perm);
            }
        }
        return user;
    }
}

