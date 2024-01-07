package lw.com.Admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.entity.Menu;
import lw.com.Admin.mapper.MenuMapper;
import lw.com.Admin.service.IMenuService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
