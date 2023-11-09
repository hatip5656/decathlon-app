package com.hatip.decathlonpoint.service;


import com.hatip.decathlonpoint.exception.ImpossibleScoreException;
import com.hatip.decathlonpoint.model.DecathlonPoint;
import com.hatip.decathlonpoint.model.request.CalculationRequest;
import com.hatip.decathlonpoint.repository.DecathlonPointRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class DecathlonPointServiceTest {

    @Mock
    private DecathlonPointRepository decathlonPointRepository;

    @InjectMocks
    private DecathlonPointService decathlonPointService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetDecathlonPointById() throws NoSuchFieldException {
        Long id = 1L;
        DecathlonPoint decathlonPoint = new DecathlonPoint();
        decathlonPoint.setId(id);

        when(decathlonPointRepository.findById(id)).thenReturn(Optional.of(decathlonPoint));

        DecathlonPoint result = decathlonPointService.getDecathlonPointById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());

        verify(decathlonPointRepository, times(1)).findById(id);
        verifyNoMoreInteractions(decathlonPointRepository);
    }

    @Test
    void testGetDecathlonPointById_NotFound() {
        Long id = 1000L;

        when(decathlonPointRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchFieldException.class, () -> decathlonPointService.getDecathlonPointById(id));

        verify(decathlonPointRepository, times(1)).findById(id);
        verifyNoMoreInteractions(decathlonPointRepository);
    }

    @Test
    void testCalculate_ValidEvent() throws NoSuchFieldException {
        // Arrange
        CalculationRequest calculationRequest = new CalculationRequest();
        calculationRequest.setEventName("Long Jump");
        calculationRequest.setScore(7.76);

        DecathlonPoint decathlonPoint = new DecathlonPoint();
        decathlonPoint.setEventName("Long Jump");
        decathlonPoint.setBasePoints(0.14354);
        decathlonPoint.setResultMultiplier(220.0);
        decathlonPoint.setResultExponent(1.4);

        when(decathlonPointRepository.findByEventName(calculationRequest.getEventName())).thenReturn(Optional.of(decathlonPoint));

        Integer result = decathlonPointService.calculate(calculationRequest);

        assertNotNull(result);
        assertEquals(1000, result);

        verify(decathlonPointRepository, times(1)).findByEventName(calculationRequest.getEventName());
        verifyNoMoreInteractions(decathlonPointRepository);
    }

    @Test
    void testCalculate_InvalidEvent() {
        CalculationRequest calculationRequest = new CalculationRequest();
        calculationRequest.setEventName("Invalid Event");

        when(decathlonPointRepository.findByEventName(calculationRequest.getEventName())).thenReturn(Optional.empty());

        assertThrows(NoSuchFieldException.class, () -> decathlonPointService.calculate(calculationRequest));

        verify(decathlonPointRepository, times(1)).findByEventName(calculationRequest.getEventName());
        verifyNoMoreInteractions(decathlonPointRepository);
    }

    @Test
    void testCalculate_ImpossibleScore() {
        // Arrange
        CalculationRequest calculationRequest = new CalculationRequest();
        calculationRequest.setEventName("Long Jump");
        calculationRequest.setScore(0.001);

        DecathlonPoint decathlonPoint = new DecathlonPoint();
        decathlonPoint.setEventName("Long Jump");
        decathlonPoint.setBasePoints(100.0);
        decathlonPoint.setResultMultiplier(2.0);
        decathlonPoint.setResultExponent(1.5);

        when(decathlonPointRepository.findByEventName(calculationRequest.getEventName())).thenReturn(Optional.of(decathlonPoint));

        // Act & Assert
        assertThrows(ImpossibleScoreException.class, () -> decathlonPointService.calculate(calculationRequest));

        verify(decathlonPointRepository, times(1)).findByEventName(calculationRequest.getEventName());
        verifyNoMoreInteractions(decathlonPointRepository);
    }

}