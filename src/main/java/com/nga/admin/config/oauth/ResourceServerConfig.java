package com.nga.admin.config.oauth;

import com.nga.admin.component.security.CustomFilterInvocationSecurityMetadataSource;
import com.nga.admin.component.security.CustomUrlDecisionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;
//    @Bean
//    OauthTokenFilter oauthTokenFilter(){
//        return new OauthTokenFilter();
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                .and()
//                .requestMatchers().antMatchers("/api/**")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/**").authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .authorizeRequests()
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
//                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
//                        object.setAccessDecisionManager(customUrlDecisionManager);
//                        return object;
//                    }
//                })
//                .anyRequest()
//                .authenticated();

        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .formLogin()
            .and()
            .authorizeRequests()
            // 配置api打头的路由需要安全认证
            .antMatchers("/api/**").authenticated()
            .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                    object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                    object.setAccessDecisionManager(customUrlDecisionManager);
                    return object;
                }
            })
            .anyRequest()
            .authenticated();

    }

}