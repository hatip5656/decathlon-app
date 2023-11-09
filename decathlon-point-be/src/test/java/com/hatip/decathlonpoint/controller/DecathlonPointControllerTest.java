package com.hatip.decathlonpoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatip.decathlonpoint.model.DecathlonPoint;
import com.hatip.decathlonpoint.model.request.CalculationRequest;
import com.hatip.decathlonpoint.service.DecathlonPointService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class DecathlonPointControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DecathlonPointService decathlonPointService;


    @InjectMocks
    private DecathlonPointController decathlonPointController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(decathlonPointController).build();
    }

    @Test
    void testGetAllDecathlonPoints() throws Exception {
        DecathlonPoint point1 = new DecathlonPoint();
        point1.setId(1L);
        DecathlonPoint point2 = new DecathlonPoint();
        point2.setId(2L);
        List<DecathlonPoint> points = Arrays.asList(point1, point2);

        when(decathlonPointService.getAllDecathlonPoints()).thenReturn(points);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/decathlon-point"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(point1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(point2.getId()));

        verify(decathlonPointService, times(1)).getAllDecathlonPoints();
        verifyNoMoreInteractions(decathlonPointService);
    }

    @Test
    void testGetDecathlonPointById() throws Exception {
        // Arrange
        Long id = 1L;
        DecathlonPoint point = new DecathlonPoint();
        point.setId(id);

        when(decathlonPointService.getDecathlonPointById(id)).thenReturn(point);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/decathlon-point/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));

        verify(decathlonPointService, times(1)).getDecathlonPointById(id);
        verifyNoMoreInteractions(decathlonPointService);
    }

    @Test
    void testCreateDecathlonPoint() throws Exception {
        DecathlonPoint point = new DecathlonPoint();
        point.setId(1L);

        when(decathlonPointService.createDecathlonPoint(any())).thenReturn(point);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/decathlon-point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(point)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        verify(decathlonPointService, times(1)).createDecathlonPoint(any());
        verifyNoMoreInteractions(decathlonPointService);
    }

    @Test
    void testUpdateDecathlonPoint() throws Exception {
        Long id = 1L;
        DecathlonPoint point = new DecathlonPoint();
        point.setId(id);

        when(decathlonPointService.updateDecathlonPoint(any(), any())).thenReturn(point);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/decathlon-point/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(point)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));

        verify(decathlonPointService, times(1)).updateDecathlonPoint(any(), any());
        verifyNoMoreInteractions(decathlonPointService);
    }

    @Test
    void testDeleteDecathlonPoint() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/decathlon-point/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(decathlonPointService, times(1)).deleteDecathlonPoint(id);
        verifyNoMoreInteractions(decathlonPointService);
    }

    @Test
    void testCalculate() throws Exception {
        CalculationRequest calculationRequest = new CalculationRequest();

        when(decathlonPointService.calculate(calculationRequest)).thenReturn(100);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/decathlon-point/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(calculationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("100"));

        verify(decathlonPointService, times(1)).calculate(calculationRequest);
        verifyNoMoreInteractions(decathlonPointService);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}