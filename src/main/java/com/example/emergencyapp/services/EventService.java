package com.example.emergencyapp.services;

import com.example.emergencyapp.DTO.EventRequestDto;
import com.example.emergencyapp.DTO.EventResponseDto;
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

    public EventResponseDto createEvent(EventRequestDto eventRequest) {
        Event event = new Event();
        event.setLocation(eventRequest.getLocation());
        event.setType(eventRequest.getType());
        event.setTime(eventRequest.getTime());
        event.setDangerLevel(eventRequest.getDangerLevel());
        Event savedEvent = eventRepository.save(event);

        return mapToResponseDto(savedEvent);
    }

    public Optional<EventResponseDto> getEventById(Long id) {
        return eventRepository.findById(id).map(this::mapToResponseDto);
    }

    public EventResponseDto updateEvent(Long id, EventRequestDto eventRequest) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setLocation(eventRequest.getLocation());
                    event.setType(eventRequest.getType());
                    event.setTime(eventRequest.getTime());
                    event.setDangerLevel(eventRequest.getDangerLevel());
                    Event updatedEvent = eventRepository.save(event);
                    return mapToResponseDto(updatedEvent);
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

    private EventResponseDto mapToResponseDto(Event event) {
        EventResponseDto responseDTO = new EventResponseDto();
        responseDTO.setId(event.getId());
        responseDTO.setLocation(event.getLocation());
        responseDTO.setType(event.getType());
        responseDTO.setTime(event.getTime());
        responseDTO.setDangerLevel(event.getDangerLevel());
        return responseDTO;
    }
}
