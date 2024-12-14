package com.example.emergencyapp;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void addEvent(Event event) {
        eventRepository.saveEvent(event);
    }

    public List<Event> getEvents() {
        return eventRepository.getAllEvents();
    }
}
