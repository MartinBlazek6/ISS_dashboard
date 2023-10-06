package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class Astronaut {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY) // Many astronauts can have one craft
    @JoinColumn(name = "craft_id")    // The foreign key column in Astronaut table
    private Craft craft;


    @Override
    public String toString() {
        return "Astronaut{" +
                "name='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
