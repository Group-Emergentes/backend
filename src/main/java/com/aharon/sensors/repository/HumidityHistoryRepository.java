package com.aharon.sensors.repository;

import com.aharon.models.entities.HumidityHistory;
import com.aharon.sensors.dto.HumidityRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HumidityHistoryRepository extends JpaRepository<HumidityHistory, Long> {
    Optional<HumidityHistory> findFirstByOrderByRegisterDateDesc();
}
