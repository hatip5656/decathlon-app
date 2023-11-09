package com.hatip.decathlonpoint.model.request;

import lombok.Data;

@Data
public class CalculationRequest {
    private String eventName;
    private Double score;
}
