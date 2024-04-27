package lw.com.Admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.dto.PageVo;
import lw.com.Admin.domain.entity.Menu;
import lw.com.Admin.domain.entity.User;
import lw.com.Admin.domain.vo.sys.SysMenuVo;
import lw.com.Admin.domain.vo.sys.SysUserVo;
import lw.com.Admin.mapper.MenuMapper;
import lw.com.Admin.service.IMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Override
    public List<SysMenuVo> getMenuList() {
        List<Menu> menus = baseMapper.selectList(null);
        List<SysMenuVo> sysMenuVos = new ArrayList<>();
        for(Menu item : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            BeanUtils.copyProperties(item,sysMenuVo);
            sysMenuVos.add(sysMenuVo);
        }
        var result = sysMenuVos.stream()
                .filter(item -> Objects.equals(item.getParentId(),0L))
                .map(item -> item.setChildren(getChild(item.getId(),sysMenuVos)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
        return result;
    }
    private List<SysMenuVo> getChild(Long id,List<SysMenuVo> menus) {
        return menus.stream()
                .filter(item -> Objects.equals(item.getParentId(),id))
                .map(item -> item.setChildren(getChild(item.getId(),menus)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
    }
}
