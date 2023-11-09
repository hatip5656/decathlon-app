package com.hatip.decathlonpoint.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "DECATHLONPOINT")
@Getter
@Setter
@NoArgsConstructor
public class DecathlonPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EVENT_NAME", unique = true)
    @NotBlank(message = "Event name is required")
    private String eventName;

    @Column(name = "BASE_POINTS")
    @NotNull(message = "Base points is required")
    @Min(value = 0, message = "Base points must be a positive number")
    private Double basePoints;

    @Column(name = "RESULT_MULTIPLIER")
    @NotNull(message = "Result multiplier is required")
    @Min(value = 0, message = "Result multiplier must be a positive number")
    private Double resultMultiplier;

    @Column(name = "RESULT_EXPONENT")
    @NotNull(message = "Result exponent is required")
    @Min(value = 0, message = "Result exponent must be a positive number")
    private Double resultExponent;

}