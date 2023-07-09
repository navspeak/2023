package com.nav.ecommerce.junitdemo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope
@Data
public class SingletonBean {
    private String property1;
    private String property2;
    private String property3;
}