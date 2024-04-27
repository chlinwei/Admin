package lw.com.Admin.domain.vo.sys;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserVo {
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
