package com.example.demo.service;

import com.example.demo.entity.Craft;
import com.example.demo.entity.Mission;
import com.example.demo.exceptions.MissionException;
import com.example.demo.repository.CraftRepository;
import com.example.demo.repository.MissionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MissionService {


    private final MissionRepository missionRepository;
    private final CraftRepository craftRepository;

    private boolean isCraftValidForMission(Mission mission, Craft craft) {
        if (mission.getMinimumCrew() > craft.getAstronauts().size()) {
            throw new MissionException(
                    craft.getCraftName() + " is not ready for mission we need " +
                            (mission.getMinimumCrew() - craft.getAstronauts().size()) + " more astronauts!");
        }
        if (mission.isDone()) {
            throw new MissionException(mission.getName() + " is already done!");
        }
        return true;
    }

    public String assignCrewToMission(Mission mission, Craft craft) {
        boolean validCraftForMission;
        try {
            validCraftForMission = isCraftValidForMission(mission, craft);
        } catch (MissionException e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
        if (validCraftForMission) {
            mission.setCraft(craft);
            craft.setMission(mission);
            missionRepository.save(mission);
            craftRepository.save(craft);
        }
        return craft.getCraftName() + " assigned to mission " + mission.getName();
    }


}
