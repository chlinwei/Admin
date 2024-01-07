package lw.com.Admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lw.com.Admin.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}