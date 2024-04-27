package lw.com.Admin.domain.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lw.com.Admin.domain.entity.Role;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


@Data
@TableName("user")
public class NewSysUserDto implements Serializable {
    private String username;
    private String nickname;
    @TableId
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
    //0表示正常，1表示被删除了
    private Integer deleted;
    private String remark;

    // 角色信息
    @TableField(exist = false)
    private List<Role> roles = new ArrayList<>();

}
