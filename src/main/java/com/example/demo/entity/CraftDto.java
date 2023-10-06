package com.example.demo.entity;

import lombok.*;

@Getter
@AllArgsConstructor
public class CraftDto {
    private String craftName;
    private int numberOfPeople;


    @Override
    public String toString() {
        return "Craft{" +
                "craftName='" + craftName + '\'' +
                ", numberOfPeopleInCraft=" + numberOfPeople +
                '}';
    }
}