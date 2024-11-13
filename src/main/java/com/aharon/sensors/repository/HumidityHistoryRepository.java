package com.aharon.sensors.repository;

import com.aharon.models.entities.HumidityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Repository
public interface HumidityHistoryRepository extends JpaRepository<HumidityHistory, Long> {

    @Query("SELECT h FROM HumidityHistory h WHERE h.zone.id = :zoneId ORDER BY h.registerDate DESC")
    List<HumidityHistory> findTop30ByZoneIdOrderByRegisterDateDesc(@Param("zoneId") Long zoneId, Pageable pageable);

    @Query("SELECT h FROM HumidityHistory h WHERE h.registerDate >= :startDate AND h.registerDate <= :endDate AND h.zone.id = :zoneId")
    List<HumidityHistory> findHumidityRecordsWithinDateRangeAndZone(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("zoneId") Long zoneId);
}
