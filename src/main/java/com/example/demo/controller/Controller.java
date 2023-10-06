package com.example.demo.controller;

import com.example.demo.entity.Astronaut;
import com.example.demo.entity.Craft;
import com.example.demo.entity.Mission;
import com.example.demo.exceptions.AstronautNotFoundException;
import com.example.demo.mappers.CraftMapper;
import com.example.demo.repository.AstronautRepository;
import com.example.demo.repository.CraftRepository;
import com.example.demo.service.AstronautService;
import com.example.demo.service.MissionService;
import com.example.demo.service.UtilService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {
    private final AstronautService astronautService;
    private final UtilService utilService;
    private final AstronautRepository astronautRepository;
    private final MissionService missionService;
    private final CraftRepository craftRepository;
    private final CraftMapper craftMapper;


    @GetMapping("/find/{name}/{lastName}")
    public String findByName(@PathVariable String name,
                      @PathVariable String lastName) {
        try {
            return astronautService.getAstronautByNameAndLastName(name, lastName).toString();
        } catch (AstronautNotFoundException e) {
            log.error(e.getMessage().toUpperCase());
            return e.getMessage();
        }
    }

    @GetMapping("/getCraftByAstronaut/{name}/{lastName}")
    public String getCraftByAstronaut(@PathVariable String name,
                         @PathVariable String lastName) {
        Craft c = craftRepository.findCraftByAstronautNameAndLastName(name, lastName);
        log.info(c.getCraftName());
        log.info(utilService.getSizeOfCraftCrew(c.getCraftName()).toString());

        return craftMapper.mapCraftToCraftDTO(c).toString();
    }

    @GetMapping("/mission/{craftName}/{missionName}")
    public String readyForMission(@PathVariable String craftName,
                                 @PathVariable String missionName) {
        Craft craft = utilService.getByCraftName(craftName);
        Mission mission = utilService.getMissionByName(missionName);

        return missionService.assignCrewToMission(mission, craft);

    }

    @PostMapping("/missionCreate/{missionDays}/{mission}/{crew}")
    public String createMission(@PathVariable String mission,
                                @PathVariable int missionDays,
                                @PathVariable int crew) {
        Mission mission1 = Mission.builder()
                .Description("random description")
                .startDate(LocalDate.now())
                .expectedFinishDate(LocalDate.now().plusDays(missionDays))
                .name(mission)
                .minimumCrew(crew)
                .isDone(false).build();

        utilService.createMission(mission1);

        return "mission " + mission + " created";

    }

    @PostMapping("/create/{name}/{lastName}")
    public String createAstronaut(@PathVariable String name,
                       @PathVariable String lastName) {
        astronautRepository.save(Astronaut.builder()
                .firstName(name)
                .lastName(lastName)
                .build());
        return "OK";
    }

    @PostMapping("/saveAstronauts")
    public String saveAstronauts() throws JsonProcessingException {
        String apiUrl = "http://api.open-notify.org/astros.json";

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode peopleNode = jsonNode.get("people");

        for (JsonNode astronautNode : peopleNode) {
            String name = astronautNode.get("name").asText();
            String craftName = astronautNode.get("craft").asText();

            String[] nameParts = name.split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts[1];

            Craft existingCraft = craftRepository.findByCraftName(craftName);
            if (existingCraft == null) {
                existingCraft = Craft.builder()
                        .craftName(craftName)
                        .astronauts(new ArrayList<>())
                        .build();
            }

            Astronaut astronaut = Astronaut.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .craft(existingCraft)
                    .build();

            existingCraft.getAstronauts().add(astronaut);
            craftRepository.save(existingCraft);
        }

        return "Astronauts saved to the database.";
    }

}
