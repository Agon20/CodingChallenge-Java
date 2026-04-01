package com.example.codingchallengejava.calculation.controller;

import com.example.codingchallengejava.calculation.service.CalculationService;
import com.example.codingchallengejava.calculation.dtos.CalculationRequest;
import com.example.codingchallengejava.calculation.dtos.CalculationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculation")
public class CalculationController {

    private final CalculationService calculationService;

    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest calculationRequest) {
        CalculationResponse calculationResponse = calculationService.calculatePremium(calculationRequest);
        return ResponseEntity.ok(calculationResponse);
    }
}
