package lw.com.Admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("menu")
@Accessors(chain = true)
public class Menu implements Serializable {

    @TableId
    private Long id;

    private Long parentId;
    private String menuName;
    private String path;
    private Integer sort;
    private String perms;
    private Integer menuType;
    private String icon;
    @TableLogic
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<Menu> children;
}