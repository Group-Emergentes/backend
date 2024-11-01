package com.aharon.sensors.repository;

import com.aharon.models.entities.LatestSensorRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LatestSensorRegisterRepository extends JpaRepository<LatestSensorRegister, String> {



}
