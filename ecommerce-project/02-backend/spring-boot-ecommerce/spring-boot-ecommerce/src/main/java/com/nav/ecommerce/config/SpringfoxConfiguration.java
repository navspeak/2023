//package com.nav.ecommerce.config;
//
//import com.google.common.collect.Lists;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Tag;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//public class SpringfoxConfiguration {
//
////    @Bean
////    public Docket api() {
////        return new Docket(DocumentationType.SWAGGER_2)
////                .select()
////                .apis(RequestHandlerSelectors.any())
////                .paths(PathSelectors.any())
////                .build();
////    }
//
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .tags(new Tag("Address Entity", "Repository for Address entities"))
//                .apiInfo(new ApiInfo("Customer Service API",
//                        "REST API of the Customer Service",
//                        "v42",
//                        null,
//                        null,
//                        null,
//                        null,
//                        Lists.newArrayList()));
//    }
//}
