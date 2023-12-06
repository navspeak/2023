package com.nav.ecommerce.java;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@Data
public class CalObj {
    BigDecimal n1;
    BigDecimal n2;
    String op;
}
