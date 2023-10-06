package com.example.demo.repository;

import com.example.demo.entity.Astronaut;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AstronautRepository extends JpaRepository<Astronaut, UUID> {
    Astronaut findByFirstNameAndLastName(String name, String lastName);
}
