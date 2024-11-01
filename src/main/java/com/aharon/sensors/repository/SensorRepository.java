package com.aharon.sensors.repository;
import com.aharon.models.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String>{

    Boolean existsSensorBySensorId(String sensorId);

}
