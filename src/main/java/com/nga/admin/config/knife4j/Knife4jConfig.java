package com.nga.admin.config.knife4j;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Data
@Slf4j
@EnableKnife4j
@EnableSwagger2WebMvc
public class Knife4jConfig {
    @Value("${server.port}")
    String port;
    @Value("${swagger.title}")
    String title;
    @Value("${swagger.description}")
    String description;
    @Value("${swagger.version}")
    String version;
    @Value("${swagger.termsOfServiceUrl}")
    String termsOfServiceUrl;
    @Value("${swagger.contact.name}")
    String contactName;
    @Value("${swagger.contact.url}")
    String contactUrl;
    @Value("${swagger.contact.email}")
    String contactEmail;
    @Value("${swagger.license}")
    String license;
    @Value("${swagger.licenseUrl}")
    String licenseUrl;
    @Bean
    public Docket docket(Environment environment) {
        //设置要显示的 Swagger 环境
        Profiles profiles = Profiles.of("dev", "test");
        //通过environment.acceptsProfiles判断是否处在自己设定的环境中
        boolean flag = environment.acceptsProfiles(profiles);
        if (flag){
            log.info("当前环境为非生产环境,swagger接口文档允许暴露");
            log.info("https://localhost:" + port + "/api");
        }
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder.name("Authorization").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(flag)
                .apiInfo(apiInfo())
                .groupName("localhost管理端")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nga.admin"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);

    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName,contactUrl,contactEmail))
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }


}
