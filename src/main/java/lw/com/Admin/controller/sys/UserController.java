package lw.com.Admin.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.dto.NewSysUserDto;
import lw.com.Admin.domain.dto.PageVo;
import lw.com.Admin.domain.entity.User;
import lw.com.Admin.domain.vo.sys.SysUserVo;
import lw.com.Admin.exception.AdminException;
import lw.com.Admin.exception.ErrorEnum;
import lw.com.Admin.service.IUserService;
import lw.com.Admin.utils.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.function.Predicate;

@Controller
@Slf4j
@RequestMapping("sys/user")
public class UserController {
    @Autowired
    private  IUserService userService;

    @GetMapping("list")
    @ResponseBody
    public Response<IPage<SysUserVo>> getUserList(@RequestBody(required = false) PageVo pageVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

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
        if(pageVo == null) {
            pageVo = new PageVo(1,20);
        }
        IPage<User> page =  Page.of(pageVo.getPage(), pageVo.getSize());
        userService.getBaseMapper().selectPage(page,queryWrapper);
        IPage<SysUserVo> resultPage = page.convert(result -> {
            SysUserVo sysUserVo = new SysUserVo();
            BeanUtils.copyProperties(result, sysUserVo);
            return sysUserVo;
        });
        return Response.newResponse_200(resultPage);
    }
    @PostMapping("add")
    @ResponseBody
    public Response<SysUserVo> addUser(@RequestBody(required = true)NewSysUserDto sysUserDto) {
        User user = new User();
        BeanUtils.copyProperties(sysUserDto,user);
        SysUserVo sysUserVo = new SysUserVo();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> condition = userLambdaQueryWrapper.eq(User::getEmail, user.getEmail()).or().eq(User::getUsername, user.getUsername());
        User queryUser = userService.getOne(condition);
        if(queryUser != null) {
            return Response.newAdminExceptionResponse(ErrorEnum.USER_EXISTS.getErrCode(), "用户名或者邮箱已存在");
        }
        userService.getBaseMapper().insert(user);
        BeanUtils.copyProperties(user,sysUserVo);
        return Response.newResponse_200(sysUserVo);

    }
    @PostMapping("del")
    @ResponseBody
    public Response<Integer> deleteUserById(@RequestBody Long id) {
        boolean b = userService.removeById(id);
        if(b) {
            return Response.newResponse_200_withoutData();
        }else {
            throw new AdminException(ErrorEnum.USER_NOT_EXISTS);
        }
    }
}
