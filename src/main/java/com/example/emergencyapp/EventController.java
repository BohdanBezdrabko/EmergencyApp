package com.example.emergencyapp;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/events")
public class EventController {

    @PostMapping("/new")
    public String addEvent(@RequestBody Event newEvent) {
        newEvent.setTime(LocalDateTime.now());
        return "Подія успішно додана: " + newEvent.getType() + " у місці "
                + newEvent.getLocation() + ", рівень небезпеки: " + newEvent.getDangerLevel();
    }
}
