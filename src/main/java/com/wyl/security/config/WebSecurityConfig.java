package com.wyl.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @Description SecurityConfig
 * @Author YiLong Wu
 * @Date 2020/2/5 15:52
 * @Version 1.0.0
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 基于方法授权需要开启
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // 配置用户信息服务 （查询用户信息）
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // 从你内存查询
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("张三").password("123").authorities("p1").build());
//        manager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
//        return manager;
//    }

    // 密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // 配置安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 解决username使用中文登录
        CharacterEncodingFilter filter1 = new CharacterEncodingFilter();
        filter1.setEncoding("utf-8");

        http.addFilterBefore(filter1, ChannelProcessingFilter.class).authorizeRequests()
                .antMatchers("/r/**").hasAnyAuthority("p1","p2")

                .antMatchers("/r/**").authenticated() // 所有/r/**的请求路径都要认证
                .anyRequest().permitAll() // 除了/r/**，其他请求允许通过
                .and()
                .formLogin() // 允许表单登录
                .loginPage("/login-view")
                .loginProcessingUrl("/login")  // 表单post提交的方法
                .successForwardUrl("/loginSuccess")// 登录成功后的跳转地址
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-view?logout")
                .invalidateHttpSession(true);

      http.csrf().disable();

    }
}
