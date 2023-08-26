package com.example.prog4.repository.main;

import com.example.prog4.repository.main.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, String> {
    Optional<Position> findPositionByNameEquals(String name);
}
