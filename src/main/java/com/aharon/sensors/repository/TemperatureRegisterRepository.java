package com.aharon.sensors.repository;

import com.aharon.models.entities.TemperatureHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TemperatureRegisterRepository extends JpaRepository<TemperatureHistory, Long> {

    @Query("SELECT t FROM TemperatureHistory t WHERE t.registerDate >= :startDate AND t.registerDate <= :endDate AND t.zone.id = :zoneId")
    List<TemperatureHistory> findTemperatureRecordsWithinDateRangeAndZone(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("zoneId") Long zoneId);


}
