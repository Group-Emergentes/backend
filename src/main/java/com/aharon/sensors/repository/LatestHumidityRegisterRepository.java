package com.aharon.sensors.repository;

import com.aharon.models.entities.LatestHumidityRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LatestHumidityRegisterRepository extends JpaRepository<LatestHumidityRegister, String> {

}
