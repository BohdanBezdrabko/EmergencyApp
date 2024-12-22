package com.example.emergencyapp.repositories;

import com.example.emergencyapp.models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EventRepositoryTest {

    @Mock
    private EventRepository eventRepository;

    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event();
        event.setId(1L);
        event.setLocation("Test Location");
        event.setType("Fire");
        event.setTime(LocalDateTime.now());
        event.setDangerLevel("High");
    }

    @Test
    void testSaveEvent() {
        when(eventRepository.save(event)).thenReturn(event);

        Event savedEvent = eventRepository.save(event);

        assertThat(savedEvent).isNotNull();
        assertThat(savedEvent.getId()).isEqualTo(1L);
        assertThat(savedEvent.getLocation()).isEqualTo("Test Location");
        assertThat(savedEvent.getType()).isEqualTo("Fire");
        assertThat(savedEvent.getDangerLevel()).isEqualTo("High");

        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testFindById() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Optional<Event> foundEvent = eventRepository.findById(1L);

        assertThat(foundEvent).isPresent();
        assertThat(foundEvent.get().getId()).isEqualTo(1L);
        assertThat(foundEvent.get().getLocation()).isEqualTo("Test Location");
        assertThat(foundEvent.get().getType()).isEqualTo("Fire");
        assertThat(foundEvent.get().getDangerLevel()).isEqualTo("High");

        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteEvent() {
        doNothing().when(eventRepository).delete(event);

        eventRepository.delete(event);

        verify(eventRepository, times(1)).delete(event);
    }
}
