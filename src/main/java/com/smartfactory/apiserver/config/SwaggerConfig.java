package com.smartfactory.apiserver.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@ConditionalOnExpression(value = "${swagger.enable:false}")
@Configuration
public class SwaggerConfig {
    private String version;
    private String title;

    @Bean
    public Docket apiV2() {
        version = "V2";
        title = "SmartFactory API " + version;

//        List<Parameter> global = new ArrayList<>();
//        global.add(new ParameterBuilder().name("Authorization").
//                description("Access Token").parameterType("header").
//                required(false).modelRef(new ModelRef("string")).build());

        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(global)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.smartfactory.apiserver.api"))
                .paths(PathSelectors.ant("/api/v2/**"))
                .build()
                .apiInfo(apiInfo(title, version));

    }

    /*@Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

    }*/

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "SmartFactory API Docs",
                version,
                "www.SmartFactory.co.kr",
                new Contact("Contact Me", "www.SmartFactory.co.kr", "SmartFactory@gmail.com"),
                "Licenses",
                "www.SmartFactory.co.kr",

                new ArrayList<>());
    }
}
