package io.changrea.restapiexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {
    private String version;
    private String title;

    @Bean
    public Docket apiV1() {
        version = "V1";
        title = "RESTful API Example";

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.changrea.restapiexample"))
                .paths(PathSelectors.ant("/v1/api/**"))
                .build()
                .apiInfo(apiInfo(title, version));
    }

    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfo(
                title,
                "ChanGrea's RESTful API Example Docs",
                version,
                "none",
                new Contact("Contact Me", "https://github.com/changrea", "k39335@naver.com"),
                "Licenses",
                "https://github.com/changrea",
                new ArrayList<>());
    }
}
