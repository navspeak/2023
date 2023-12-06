package com.nav.ecommerce.java;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@Data
public class ResponseObj {
    private BigDecimal value;
    private String error;
}