package com.example.emergencyapp.services;

import com.example.emergencyapp.models.Event;
import com.example.emergencyapp.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setLocation(updatedEvent.getLocation());
                    event.setType(updatedEvent.getType());
                    event.setTime(updatedEvent.getTime());
                    event.setDangerLevel(updatedEvent.getDangerLevel());
                    return eventRepository.save(event);
                })
                .orElseThrow(() -> new RuntimeException("Event with ID " + id + " not found."));
    }

    public void deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new RuntimeException("Event with ID " + id + " not found.");
        }
    }
}
