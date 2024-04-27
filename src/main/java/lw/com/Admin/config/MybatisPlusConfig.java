package lw.com.Admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

    @Configuration
    public class MybatisPlusConfig {
        @Bean
        public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) List ddlList) {
            return new DdlApplicationRunner(ddlList);
        }

//        分页查询配置
        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
            var mybatisPlusInterceptor = new MybatisPlusInterceptor();
            mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            return mybatisPlusInterceptor;
        }
    }

