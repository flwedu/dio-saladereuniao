package com.digital.one.saladereuniao.service;

import java.util.List;

import com.digital.one.saladereuniao.model.Event;
import com.digital.one.saladereuniao.repository.EventRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class EventServiceTest {

    private static EventRepository repository;

    private static EventService service;

    @BeforeEach
    public void setup() {
        repository = mock(EventRepository.class);
        service = new EventService(repository);
    }

    @Test
    public void shouldReturnAllEventsListByRoom() {

        PageImpl<Event> returnedPage = new PageImpl<>(List.of(mock(Event.class)));
        PageRequest page = PageRequest.of(0, 5);
        when(repository.findAllByRoomId(anyLong(), any(Pageable.class))).thenReturn(returnedPage);

        Page<Event> events = service.findAllByRoomId(1L, page);

        verify(repository, times(1)).findAllByRoomId(1L, page);
        assertEquals(events.getNumberOfElements(), 1);
        assertEquals(events.getTotalPages(), 1);
    }

    @Test
    public void shouldReturnAllEventsByPage() {

        PageImpl<Event> returnedPage = new PageImpl<>(List.of(mock(Event.class)));
        when(repository.findAll(any(Pageable.class))).thenReturn(returnedPage);

        PageRequest page = PageRequest.of(0, 5);

        Page<Event> events = service.findAll(page);

        verify(repository, times(1)).findAll(page);
        assertEquals(events.getNumberOfElements(), 1);
        assertEquals(events.getTotalPages(), 1);
    }

}
