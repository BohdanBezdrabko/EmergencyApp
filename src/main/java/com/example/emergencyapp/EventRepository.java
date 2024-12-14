package com.example.emergencyapp;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepository {
    private List<Event> events = new ArrayList<>();

    public void saveEvent(Event event) {
        events.add(event);
    }

    public List<Event> getAllEvents() {
        return events;
    }
}
