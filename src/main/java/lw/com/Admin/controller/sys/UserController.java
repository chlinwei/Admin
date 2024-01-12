package lw.com.Admin.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.dto.PageVo;
import lw.com.Admin.domain.entity.User;
import lw.com.Admin.domain.vo.sys.SysUser;
import lw.com.Admin.service.IUserService;
import lw.com.Admin.utils.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.function.Predicate;

@Controller
@Slf4j
@RequestMapping("sys")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @GetMapping("user/list")
    @ResponseBody
    public Response<IPage<SysUser>> getUserList(@RequestBody PageVo pageVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(User::getUsername,"ad");
        queryWrapper.select(User.class, new Predicate<TableFieldInfo>() {
            @Override
            public boolean test(TableFieldInfo tableFieldInfo) {
                if(tableFieldInfo.getColumn().equals("role_set") || tableFieldInfo.getColumn().equals("perms") ){
                    return  false;
                }else {
                    return  true;
                }

            }
        });
        IPage<User> page =  Page.of(pageVo.getPage(), pageVo.getSize());
        userService.getBaseMapper().selectPage(page,queryWrapper);
        IPage<SysUser> resultPage = page.convert(result -> {
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(result, sysUser);
            return sysUser;
        });

        return Response.newResponse_200(resultPage);
    }
    @GetMapping("user/add")
    @ResponseBody
    public Response<SysUser> AddUser() {
        return null;
    }
}
