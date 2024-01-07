package lw.com.Admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.entity.Role;
import lw.com.Admin.mapper.RoleMapper;
import lw.com.Admin.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
}
