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

        when(repository.findAllByRoomId(anyLong())).thenReturn(List.of(mock(Event.class)));

        List<Event> events = service.findAllByRoomId(1L);

        Mockito.verify(repository, Mockito.times(1)).findAllByRoomId(1L);
        Assertions.assertFalse(events.isEmpty());
    }

}
