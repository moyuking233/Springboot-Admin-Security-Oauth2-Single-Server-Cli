package com.nga.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nga.admin.common.utils.R;
import com.nga.admin.config.security.SecurityConfig;
import com.nga.admin.entity.vo.LoginUserVo;
import com.nga.admin.service.IPermissionService;
import com.nga.admin.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.validation.Valid;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("api/login")
public class LoginController {
    @Autowired
    SecurityConfig securityConfig;
    @Autowired
    IPermissionService permissionService;
    @Autowired
    ConsumerTokenServices consumerTokenServices;
    @Autowired
    IUserService userService;


    /**
     * ??????????????????HTTPS?????????Restemplate
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws KeyStoreException
     */
    private HttpComponentsClientHttpRequestFactory generateHttpRequestFactory()
            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException
    {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        return factory;
    }

    /**
     * ????????????
     * @Description
     * ???????????????/oauth/token?????????????????????token????????????????????????????????? ????????????????????????????????????????????????
     * @param vo
     * @return
     */
    @PostMapping("/")
    @ApiOperation(value = "????????????token")
    public R login(@Valid LoginUserVo vo){
        MultiValueMap<String, Object> bodys = new LinkedMultiValueMap<>();
        bodys.add("username",vo.getUsername());
        bodys.add("password",vo.getPassword());
        String host = "https://localhost:8753";
        String path = "/oauth/token";
        bodys.add("client_secret", securityConfig.getClientSecret());
        bodys.add("client_id", securityConfig.getClientId());
        bodys.add("grant_type", securityConfig.getGrantType());
        try {
            RestTemplate restTemplate = new RestTemplate(generateHttpRequestFactory());
            HttpHeaders requestHeaders = new HttpHeaders();//???????????????
            requestHeaders.set("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");//?????????????????????
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(bodys, requestHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(host+path, HttpMethod.POST, httpEntity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK){//????????????????????????
                String body = responseEntity.getBody();
                log.debug("????????????token??????");
                JSONObject resultMap = JSONObject.parseObject(body);
//                userService.updateLastLoginByUsername(vo.getUsername());
                return R.ok().put("data",resultMap);
            }
            return R.error("????????????");
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("??????token??????");
            return R.error("????????????");
        }
    }

    @GetMapping("/menu")
    @ApiOperation(value = "????????????????????????????????????")
    public R menu(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject json = (JSONObject) JSON.toJSON(authentication);
        JSONObject userAuthentication = (JSONObject) JSON.toJSON(json.get("userAuthentication"));
        JSONObject user = userAuthentication.getJSONObject("principal");
        Integer posId = user.getInteger("posId");
        //TODO ?????????????????????????????????
        //        return R.ok("????????????").put(permissionService.getAllPermissionsByPosId(posId));
        return R.ok("????????????").put("?????????????????????");
    }


//    @GetMapping("/userinfo")
//    public R userinfo(Principal principal) {
//        JSONObject json = (JSONObject) JSON.toJSON(principal);
//        JSONObject user = json.getJSONObject("principal");
//        return R.ok().put("user",user);
//    }


    @DeleteMapping(value = "/logout")
    @ApiOperation(value = "????????????")
    public R revokeToken(@RequestHeader("Authorization") String access_token) {
        if (access_token.length()>7){
             access_token = access_token.substring(7,access_token.length());
            if (consumerTokenServices.revokeToken(access_token)) {
                return R.ok("????????????");
            }
        }
        return R.error("????????????");
    }

}
