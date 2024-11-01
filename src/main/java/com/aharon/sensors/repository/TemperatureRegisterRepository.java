package com.aharon.sensors.repository;

import com.aharon.models.entities.TemperatureHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRegisterRepository extends JpaRepository<TemperatureHistory, Long> {

}
