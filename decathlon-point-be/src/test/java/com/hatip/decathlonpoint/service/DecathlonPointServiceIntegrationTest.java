package com.hatip.decathlonpoint.service;


import com.hatip.decathlonpoint.exception.ImpossibleScoreException;
import com.hatip.decathlonpoint.model.DecathlonPoint;
import com.hatip.decathlonpoint.model.request.CalculationRequest;
import com.hatip.decathlonpoint.repository.DecathlonPointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "classpath:db/test.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class DecathlonPointServiceIntegrationTest {

    @Autowired
    private DecathlonPointService decathlonPointService;
    @Autowired
    private DecathlonPointRepository decathlonPointRepository;


    @Test
    void testGetDecathlonPointById() throws NoSuchFieldException {
        DecathlonPoint data = decathlonPointRepository.findAll().get(0);
        DecathlonPoint result = decathlonPointService.getDecathlonPointById(data.getId());

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(data.getEventName(), result.getEventName());
        assertEquals(data.getBasePoints(), result.getBasePoints());
        assertEquals(data.getResultMultiplier(), result.getResultMultiplier());
        assertEquals(data.getResultExponent(), result.getResultExponent());
    }

    @Test
    void testGetDecathlonPointById_NotFound() {
        assertThrows(NoSuchFieldException.class, () -> decathlonPointService.getDecathlonPointById(1000L));
    }

    @Test
    void testCalculate_ValidEvent() throws NoSuchFieldException {
        CalculationRequest calculationRequest = new CalculationRequest();
        calculationRequest.setEventName("Long jump");
        calculationRequest.setScore(7.76);


        Integer result = decathlonPointService.calculate(calculationRequest);

        assertNotNull(result);
        assertEquals(1000, result);

    }

    @Test
    void testCalculate_InvalidEvent() {
        CalculationRequest calculationRequest = new CalculationRequest();
        calculationRequest.setEventName("Invalid Event");

        assertThrows(NoSuchFieldException.class, () -> decathlonPointService.calculate(calculationRequest));
    }

    @Test
    void testCalculate_ImpossibleScore() {
        CalculationRequest calculationRequest = new CalculationRequest();
        calculationRequest.setEventName("Long jump");
        calculationRequest.setScore(0.001);

        assertThrows(ImpossibleScoreException.class, () -> decathlonPointService.calculate(calculationRequest));

    }

}