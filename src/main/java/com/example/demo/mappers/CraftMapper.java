package com.example.demo.mappers;

import com.example.demo.entity.Craft;
import com.example.demo.entity.CraftDto;
import org.springframework.stereotype.Service;

@Service
public class CraftMapper {
    public CraftDto mapCraftToCraftDTO(Craft craft) {
        return new CraftDto(craft.getCraftName(), craft.getAstronauts().size());
    }
}
