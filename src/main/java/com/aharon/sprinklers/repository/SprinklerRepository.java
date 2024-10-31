package com.aharon.sprinklers.repository;
import com.aharon.models.entities.Sprinkler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprinklerRepository extends JpaRepository<Sprinkler, Long> {
}
