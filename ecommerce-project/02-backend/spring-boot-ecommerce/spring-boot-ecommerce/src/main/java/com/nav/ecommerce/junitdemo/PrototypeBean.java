package com.nav.ecommerce.junitdemo;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value= ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Data
public class PrototypeBean {
    private String property1;
    private String property2;
    private String property3;
}