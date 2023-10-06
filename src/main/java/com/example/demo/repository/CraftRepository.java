package com.example.demo.repository;

import com.example.demo.entity.Craft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CraftRepository extends JpaRepository<Craft, Long> {
    boolean existsByCraftName(String name);
    Craft findByCraftName(String name);

    @Query("SELECT c FROM Craft c JOIN c.astronauts a WHERE a.firstName = :firstName AND a.lastName = :lastName")
    Craft findCraftByAstronautNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
