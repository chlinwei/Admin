package lw.com.Admin.domain.vo.sys;

import lombok.Data;
import lw.com.Admin.domain.entity.Role;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class SysUser {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private Integer sex;
    private String avatar;
    private Integer status;
    private Long creator;
    private Long updater;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String remark;
}
