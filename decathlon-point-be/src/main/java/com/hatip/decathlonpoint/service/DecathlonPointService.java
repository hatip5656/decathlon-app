package com.hatip.decathlonpoint.service;

import com.hatip.decathlonpoint.exception.ImpossibleScoreException;
import com.hatip.decathlonpoint.model.DecathlonPoint;
import com.hatip.decathlonpoint.model.request.CalculationRequest;
import com.hatip.decathlonpoint.repository.DecathlonPointRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DecathlonPointService {

    private final DecathlonPointRepository decathlonPointRepository;

    public List<DecathlonPoint> getAllDecathlonPoints() {
        return decathlonPointRepository.findAll();
    }

    public DecathlonPoint getDecathlonPointById(Long id) throws NoSuchFieldException {
        Optional<DecathlonPoint> optionalDecathlonPoint = decathlonPointRepository.findById(id);
        return optionalDecathlonPoint.orElseThrow( ()->new NoSuchFieldException("No record found!"));
    }

    public DecathlonPoint createDecathlonPoint(DecathlonPoint decathlonPoint) {
        return decathlonPointRepository.save(decathlonPoint);
    }

    public DecathlonPoint updateDecathlonPoint(Long id, DecathlonPoint decathlonPoint) throws NoSuchFieldException {
        Optional<DecathlonPoint> optionalDecathlonPoint = decathlonPointRepository.findById(id);
        if (optionalDecathlonPoint.isPresent()) {
            DecathlonPoint existingDecathlonPoint = optionalDecathlonPoint.get();
            existingDecathlonPoint.setEventName(decathlonPoint.getEventName());
            existingDecathlonPoint.setBasePoints(decathlonPoint.getBasePoints());
            existingDecathlonPoint.setResultMultiplier(decathlonPoint.getResultMultiplier());
            existingDecathlonPoint.setResultExponent(decathlonPoint.getResultExponent());
            return decathlonPointRepository.save(existingDecathlonPoint);
        } else {
            throw new NoSuchFieldException("Record not found");
        }
    }

    public void deleteDecathlonPoint(Long id) {
        decathlonPointRepository.deleteById(id);
    }

    public Integer calculate(CalculationRequest calculationRequest) throws NoSuchFieldException {
        var event = decathlonPointRepository.findByEventName(calculationRequest.getEventName());
        if (event.isPresent()) {
            Double result = calculatePoints(calculationRequest, event.get());
            if (result.isNaN()) {
                throw new ImpossibleScoreException("This score is impossible!!");
            }
            return result.intValue();
        } else {
            throw new NoSuchFieldException("No record present");
        }
    }



    private  Double calculatePoints(CalculationRequest calculationRequest, DecathlonPoint event) {
        return event.getBasePoints() * Math.pow((calculationRequest.getScore() *100 - event.getResultMultiplier()), event.getResultExponent());
    }
}