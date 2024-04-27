package lw.com.Admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lw.com.Admin.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectUserByUsername(@Param("username") String username);
}
