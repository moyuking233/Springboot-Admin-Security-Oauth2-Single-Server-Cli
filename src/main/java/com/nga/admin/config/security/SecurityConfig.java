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
    //?????????????????????md5?????????????????????????????????
    @Value("${security.salt}")
    String salt;

    //????????????????????????oauth2.0????????????
    //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????id????????????????????????
    //?????????????????????????????????
    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????
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
    //??????????????????
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/login");
//    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
//    //????????????????????????????????????
//		http.authorizeRequests().antMatchers("/**").permitAll();
//    //????????????????????????????????????
//		http.authorizeRequests().antMatchers("/**").authenticated();
//    //?????????test???????????????????????????????????????
//		http.authorizeRequests().antMatchers("/test/**").authenticated();
//    ??????????????????test???????????????????????????????????????????????????????????????????????????requestMatchers().anyRequest()???????????????????????????????????????
//	  http.requestMatchers().anyRequest().and().authorizeRequests().antMatchers("/test/*").authenticated();
//      ?????????/test ???????????????????????????????????????????????????????????????????????????
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
