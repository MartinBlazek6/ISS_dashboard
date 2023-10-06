package com.example.demo.service;

import com.example.demo.entity.Astronaut;
import com.example.demo.exceptions.AstronautNotFoundException;
import com.example.demo.repository.AstronautRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AstronautService {

    private final AstronautRepository astronautRepository;

    public Astronaut getAstronautByNameAndLastName(String name, String lastName) {
        if (astronautRepository.findByFirstNameAndLastName(name, lastName) != null) {
            return astronautRepository.findByFirstNameAndLastName(name, lastName);
        }
        throw new AstronautNotFoundException("astronaut not found: " + name + " " + lastName);
    }
}
