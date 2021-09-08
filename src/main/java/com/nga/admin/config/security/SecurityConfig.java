package com.nga.admin.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nga.admin.common.utils.R;
import com.nga.admin.component.security.CustomFilterInvocationSecurityMetadataSource;
import com.nga.admin.component.security.CustomUrlDecisionManager;
import com.nga.admin.component.security.MD5PasswordEncoder;
import com.nga.admin.entity.po.User;
import com.nga.admin.service.IUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@Data
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //从配置文件读取md5盐，可以置空即为不加盐
    @Value("${security.salt}")
    String salt;

    //以下三个字段是给oauth2.0登录用的
    //此处的单点登录目的用于用户可以访问多个微服务，因此客户端密码，授权类型，以及id都是一样只有一条
    //因此提前在配置文件写死
    /**
     * 当然也可以在数据库直接获取，前提是只有一条记录，也即一个客户代理类型
     */
    @Value("${security.clientSecret}")
    String clientSecret;
    @Value("${security.clientId}")
    String clientId;
    @Value("${security.grantType}")
    String grantType;

    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;


    @Autowired
    IUserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        MD5PasswordEncoder md5PasswordEncoder = new MD5PasswordEncoder();
        md5PasswordEncoder.getDigester().setSalt(salt.getBytes());
        return md5PasswordEncoder;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
    //忽略匹配地址
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/login");
//    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
//    //所有接口都不需要权限认证
//		http.authorizeRequests().antMatchers("/**").permitAll();
//    //所有接口都要进行权限认证
//		http.authorizeRequests().antMatchers("/**").authenticated();
//    //只有以test开头的接口需要进行权限认证
//		http.authorizeRequests().antMatchers("/test/**").authenticated();
//    只允许路由由test开头的需要进行权限认证，其他的接口不需要权限认证；requestMatchers().anyRequest()即所有接口可以不进行认证；
//	  http.requestMatchers().anyRequest().and().authorizeRequests().antMatchers("/test/*").authenticated();
//      只有以/test 开头的路由需要进行权限认证；其他路由不需要权限认证
//	http.requestMatchers().antMatchers("/test/**").and().authorizeRequests().antMatchers("/**").authenticated();
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/oauth/**","/api/login/","/doc.html"
                ,"/swagger-ui.html"
                , "/webjars/**"
                , "/v2/**"
                , "/swagger-resources/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
//                .antMatchers("/register").authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
