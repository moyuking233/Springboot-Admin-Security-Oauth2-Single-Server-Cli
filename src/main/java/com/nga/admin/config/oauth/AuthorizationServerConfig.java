package com.nga.admin.config.oauth;




import com.nga.admin.config.security.SecurityConfig;
import com.nga.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private IUserService userService;
    @Primary
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //如果有必要可以直接配置在内存中，这里配置到数据库里，数据库需要的文件都在resource了
        //说句实话啊，由于我们的客户端代理只有一个，也就是浏览器，在数据库也就只有一条记录，也即需要发令牌的用户类型只有一种，所以完全可以在内存里配置
        /**
         * 这里解释一下四种授权模式
         * 授权码（authorization-code）
         * 隐藏式（implicit）
         * 密码式（password）
         * 客户端凭证（client credentials）
         *
         * 详细的可以看网上的博客，这里只做概括总结，字长不看
         *
         * 授权码模式，用户同意授权后，返回授权码给后端，后端发给浏览器（也即客户端，客户代理），客户端还要凭借授权码再和认证服务器拿token
         * 隐藏式：有些Web应用纯前端，C7N部署，前端也即客户端代理发起，不经过后端，用户授权后直接返回前端token
         * 密码式：用户给账号密码给客户端，认证服务器收到验证，发送token
         * 客户端凭证：很少，客户端自己和第三方服务器请求认证，拿到token，和用户一点卵关系都没有
         *
         */
         clients.withClientDetails(clientDetails());
//        clients.inMemory()
//                .withClient("android")
//                .scopes("read")
//                .secret(new BCryptPasswordEncoder().encode("android"))
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//                .and()
//                .withClient("webapp")
//                .scopes("read")
//                .authorizedGrantTypes("implicit")
//                .and()
//                .withClient("browser")
//                .authorizedGrantTypes("refresh_token", "password")
//                .scopes("read");
    }
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

//    捕获异常
//    @Bean
//    public WebResponseExceptionTranslator webResponseExceptionTranslator(){
//        return new MssWebResponseExceptionTranslator();
//    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                //TODO 可以在这里选择是否开启jwtToken增强，开启后返回的jwt令牌
//                .tokenEnhancer(jwtTokenEnhancer())
                .userDetailsService(userService)
                .authenticationManager(authenticationManager);
        endpoints.tokenServices(defaultTokenServices());
        //认证异常翻译
        // endpoints.exceptionTranslator(webResponseExceptionTranslator());
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        //tokenServices.setClientDetailsService(clientDetails());
        // token有效期自定义设置，默认12小时,此处设置为一周
        tokenServices.setAccessTokenValiditySeconds(60*60*24*7);
        // refresh_token默认30天
//        tokenServices.setRefreshTokenValiditySeconds(60 * 60);
        return tokenServices;
    }


}
