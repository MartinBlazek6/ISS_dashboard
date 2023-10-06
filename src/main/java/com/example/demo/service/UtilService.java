package com.example.demo.service;

import com.example.demo.entity.Craft;
import com.example.demo.entity.Mission;
import com.example.demo.repository.AstronautRepository;
import com.example.demo.repository.CraftRepository;
import com.example.demo.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilService {
    private final AstronautRepository astronautRepository;
    private final CraftRepository craftRepository;
    private final MissionRepository missionRepository;

    public List<Craft> getAllCrafts() {
        return craftRepository.findAll();
    }

    public Integer getSizeOfCraftCrew(String craftName) {
        return craftRepository.findByCraftName(craftName).getAstronauts().size();
    }

    public Craft getByCraftName(String name) {
        return craftRepository.findByCraftName(name);
    }

    public Mission getMissionByName(String mission) {
        return missionRepository.getByName(mission);
    }

    public void createMission(Mission mission) {
        missionRepository.save(mission);
    }


}
