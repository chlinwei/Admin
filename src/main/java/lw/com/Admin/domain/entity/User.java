package lw.com.Admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@TableName("user")
public class User implements Serializable, UserDetails {

    @TableId
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private Integer sex;
    private String avatar;
    private String password;
    private Integer status;
    private Long creator;
    private Long updater;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
    private String remark;

    // 角色信息
    @TableField(exist = false)
    private List<Role> roleSet = new ArrayList<>();
    //权限的信息
    @TableField(exist = false)
    private List<String> perms = new ArrayList<>();

    /**
     * SpringSecurity根据 getAuthorities 方法获取当前用户的权限信息
     * @return
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将权限告知SpringSecurity，通过lambda表达式将Set<String>转成Collection<GrantedAuthority>
        if(perms != null && perms.size() > 0) {
            // 返回权限信息
            return perms.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status == 0;
    }

    @Override
    public boolean isEnabled() {
        return status == 0;
    }
}
