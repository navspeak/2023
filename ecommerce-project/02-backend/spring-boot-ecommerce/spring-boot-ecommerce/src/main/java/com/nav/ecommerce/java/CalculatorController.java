package com.nav.ecommerce.java;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/calc")
public class CalculatorController {

    private CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<?> calculate(@RequestBody CalObj calculateObj){
        System.out.println(calculateObj.getN1());
        System.out.println(calculateObj.getN2());
        System.out.println(calculateObj.getOp());
        Optional<BigDecimal> val = calculatorService.performCalculation(calculateObj.getN1(), calculateObj.getN2(), calculateObj.getOp());
        ResponseObj responseObj = new ResponseObj();
        if (val.isPresent()){
            responseObj.setError(null);
            responseObj.setValue(val.get());
            return new ResponseEntity<ResponseObj>(responseObj, HttpStatus.OK);
        }
        responseObj.setError("Unsupported Operator");
        responseObj.setValue(null);
        return new ResponseEntity<ResponseObj>(responseObj, HttpStatus.BAD_REQUEST);
    }
}

