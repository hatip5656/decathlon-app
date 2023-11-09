package com.hatip.decathlonpoint.repository;

import com.hatip.decathlonpoint.model.DecathlonPoint;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecathlonPointRepository extends JpaRepository<DecathlonPoint,Long> {
    Optional<DecathlonPoint> findByEventName(String eventName);
}
