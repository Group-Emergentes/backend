package com.aharon.sprinklers.repository;
import com.aharon.sprinklers.model.Sprinkler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprinklerRepository extends JpaRepository<Sprinkler, String> {

    Boolean existsSprinklerBySprinklerId(String sprinklerId);

    List<Sprinkler> findAllByZone_Id(Long zoneId);
}
