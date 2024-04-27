package lw.com.Admin.domain.vo.sys;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuVo{
    private Long id;
    private Long parentId;
    private String menuName;
    private String path;
    private Integer sort;
    private String perms;
    private Integer menuType;
    private String icon;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<SysMenuVo> children;
}