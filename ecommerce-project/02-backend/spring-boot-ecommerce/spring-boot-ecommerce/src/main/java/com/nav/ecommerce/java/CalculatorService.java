package com.nav.ecommerce.java;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CalculatorService {

    Optional<BigDecimal> performCalculation(BigDecimal n1, BigDecimal n2, String op){
        switch (op){
            case "/":
                return Optional.ofNullable(n1.divide(n2));
            default:
                return Optional.empty();
        }
    }
}
