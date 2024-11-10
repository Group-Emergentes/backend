package com.aharon.sprinklers.repository;

import com.aharon.sprinklers.model.SprinklerActivationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprinklerActivationHistoryRepository extends JpaRepository<SprinklerActivationHistory, Long> {



}
