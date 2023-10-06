package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String Description;
    private LocalDate startDate;
    private LocalDate expectedFinishDate;
    private LocalDate finishDate;
    private int minimumCrew;
    private boolean isDone;

    @ManyToOne
    private Craft craft;

}
