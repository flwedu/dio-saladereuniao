package com.digital.one.saladereuniao.service;

import java.util.List;

import com.digital.one.saladereuniao.model.Event;
import com.digital.one.saladereuniao.repository.EventRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EventServiceTest {

    private static EventRepository repository;

    private static EventService service;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(EventRepository.class);
        service = new EventService(repository);
    }

    @Test
    public void shouldReturnAllEventsListByRoom() {

        Mockito.when(repository.findAllByRoomId(Mockito.anyLong())).thenReturn(List.of(Mockito.mock(Event.class)));

        List<Event> events = service.findAllByRoomId(1L);

        Mockito.verify(repository, Mockito.times(1)).findAllByRoomId(1L);
        Assertions.assertFalse(events.isEmpty());
    }

}
