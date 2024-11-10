package com.aharon.sensors.repository;
import com.aharon.sensors.model.entities.Sensor;
import com.aharon.zones.model.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String>{

    Boolean existsSensorBySensorId(String sensorId);

    List<Sensor> findAllByZone_Id(Long zoneId);

}
