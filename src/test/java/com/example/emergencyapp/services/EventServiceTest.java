package com.example.emergencyapp.services;

import com.example.emergencyapp.DTO.EventRequestDto;
import com.example.emergencyapp.DTO.EventResponseDto;
import com.example.emergencyapp.models.Event;
import com.example.emergencyapp.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    private EventRepository eventRepository;
    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventRepository = mock(EventRepository.class);
        eventService = new EventService(eventRepository);
    }

    @Test
    void testCreateEvent() {
        EventRequestDto requestDto = new EventRequestDto();
        requestDto.setLocation("Test Location");
        requestDto.setType("Test Type");
        requestDto.setTime(LocalDateTime.now());
        requestDto.setDangerLevel("High");

        Event savedEvent = new Event();
        savedEvent.setId(1L);
        savedEvent.setLocation(requestDto.getLocation());
        savedEvent.setType(requestDto.getType());
        savedEvent.setTime(requestDto.getTime());
        savedEvent.setDangerLevel(requestDto.getDangerLevel());

        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        EventResponseDto response = eventService.createEvent(requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Location", response.getLocation());
        assertEquals("Test Type", response.getType());
        assertEquals("High", response.getDangerLevel());

        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testGetEventById() {
        Event event = new Event();
        event.setId(1L);
        event.setLocation("Test Location");
        event.setType("Test Type");
        event.setTime(LocalDateTime.now());
        event.setDangerLevel("Low");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<EventResponseDto> response = eventService.getEventById(1L);

        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getId());
        assertEquals("Test Location", response.get().getLocation());
        assertEquals("Low", response.get().getDangerLevel());

        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateEvent() {
        EventRequestDto requestDto = new EventRequestDto();
        requestDto.setLocation("Updated Location");
        requestDto.setType("Updated Type");
        requestDto.setTime(LocalDateTime.now());
        requestDto.setDangerLevel("Critical");

        Event existingEvent = new Event();
        existingEvent.setId(1L);
        existingEvent.setLocation("Old Location");
        existingEvent.setType("Old Type");
        existingEvent.setTime(LocalDateTime.now().minusDays(1));
        existingEvent.setDangerLevel("Medium");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EventResponseDto response = eventService.updateEvent(1L, requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Updated Location", response.getLocation());
        assertEquals("Updated Type", response.getType());
        assertEquals("Critical", response.getDangerLevel());

        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(existingEvent);
    }

    @Test
    void testDeleteEvent() {
        when(eventRepository.existsById(1L)).thenReturn(true);

        eventService.deleteEvent(1L);

        verify(eventRepository, times(1)).existsById(1L);
        verify(eventRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEventNotFound() {
        when(eventRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> eventService.deleteEvent(1L));

        assertEquals("Event with ID 1 not found.", exception.getMessage());
        verify(eventRepository, times(1)).existsById(1L);
        verify(eventRepository, never()).deleteById(anyLong());
    }
}
