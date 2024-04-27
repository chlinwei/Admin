package lw.com.Admin.controller.sys;

import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.entity.Menu;
import lw.com.Admin.domain.vo.sys.SysMenuVo;
import lw.com.Admin.service.IMenuService;
import lw.com.Admin.service.IUserService;
import lw.com.Admin.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("sys/menu")
public class MenuController {
    @Autowired
    private final IMenuService menuService;

    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }
    @GetMapping("/list")
    @ResponseBody
    public Response<List<SysMenuVo>> getMenuList() {
        var menus = this.menuService.getMenuList();
        var result = Response.newResponse_200(menus);
        return result;
    }
}
