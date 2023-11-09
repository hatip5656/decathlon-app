package com.hatip.decathlonpoint.controller;

import com.hatip.decathlonpoint.model.DecathlonPoint;
import com.hatip.decathlonpoint.model.request.CalculationRequest;
import com.hatip.decathlonpoint.service.DecathlonPointService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/decathlon-point")
@RequiredArgsConstructor
public class DecathlonPointController {

    private final DecathlonPointService decathlonPointService;

    @GetMapping
    public ResponseEntity<List<DecathlonPoint>> getAllDecathlonPoints() {
        List<DecathlonPoint> decathlonPoints = decathlonPointService.getAllDecathlonPoints();
        return ResponseEntity.ok(decathlonPoints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DecathlonPoint> getDecathlonPointById(@PathVariable Long id) throws NoSuchFieldException {
        DecathlonPoint decathlonPoint = decathlonPointService.getDecathlonPointById(id);
        if (decathlonPoint != null) {
            return ResponseEntity.ok(decathlonPoint);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DecathlonPoint> createDecathlonPoint(@RequestBody DecathlonPoint decathlonPoint) {
        DecathlonPoint createdDecathlonPoint = decathlonPointService.createDecathlonPoint(decathlonPoint);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDecathlonPoint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DecathlonPoint> updateDecathlonPoint(@PathVariable Long id, @RequestBody DecathlonPoint decathlonPoint)
            throws NoSuchFieldException {
        DecathlonPoint updatedDecathlonPoint = decathlonPointService.updateDecathlonPoint(id, decathlonPoint);
        if (updatedDecathlonPoint != null) {
            return ResponseEntity.ok(updatedDecathlonPoint);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDecathlonPoint(@PathVariable Long id) {
        decathlonPointService.deleteDecathlonPoint(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calculate")
    public ResponseEntity<Integer> calculate(@RequestBody CalculationRequest calculationRequest) {
        try {
            Integer points = decathlonPointService.calculate(calculationRequest);
            return ResponseEntity.ok(points);
        } catch (NoSuchFieldException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}