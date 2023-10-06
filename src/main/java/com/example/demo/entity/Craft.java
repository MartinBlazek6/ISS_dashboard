package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class Craft {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String craftName;

    @OneToMany(mappedBy = "craft", cascade = CascadeType.ALL)
    private List<Astronaut> astronauts;

    @OneToOne
    Mission mission;

}

