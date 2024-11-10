package com.aharon.sensors.repository;

import com.aharon.models.entities.LatestSensorRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LatestSensorRegisterRepository extends JpaRepository<LatestSensorRegister, String> {

    LatestSensorRegister findBySensorId(String sensorId);

    List<LatestSensorRegister> findBySensorIdIn(List<String> list);
}
