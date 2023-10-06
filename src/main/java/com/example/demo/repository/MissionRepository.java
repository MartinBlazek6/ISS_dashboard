package com.example.demo.repository;

import com.example.demo.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MissionRepository extends JpaRepository<Mission, UUID> {
    public Mission getByName(String name);
}
