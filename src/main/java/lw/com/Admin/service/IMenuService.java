package lw.com.Admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import lw.com.Admin.domain.dto.LoginParams;
import lw.com.Admin.domain.entity.Menu;
import lw.com.Admin.domain.vo.sys.SysMenuVo;

import java.util.List;

public interface IMenuService extends IService<Menu> {
    List<SysMenuVo> getMenuList();

}