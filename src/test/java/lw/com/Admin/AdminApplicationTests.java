package lw.com.Admin;

import lombok.extern.slf4j.Slf4j;
import lw.com.Admin.domain.entity.Role;
import lw.com.Admin.domain.entity.User;
import lw.com.Admin.mapper.RoleMapper;
import lw.com.Admin.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AdminApplicationTests {
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserMapper userMapper;
//	contextLoads
	@Test
	void f2() {
		Role role = roleMapper.selectById(1);
		log.info("{}",role);
	}
	@Test
	void f1() {
		User user = userMapper.selectUserByUsername("admin");
		log.info("{}",user);
	}


}
