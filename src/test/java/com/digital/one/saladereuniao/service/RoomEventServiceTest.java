package com.digital.one.saladereuniao.service;

import java.util.List;
import java.util.Optional;

import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.repository.RoomEventRepository;
import com.digital.one.saladereuniao.utils.RoomEventFaker;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class RoomEventServiceTest {

    private static RoomEventRepository repository;

    private static RoomEventService service;

    @BeforeEach
    public void setup() {
        repository = mock(RoomEventRepository.class);
        service = new RoomEventService(repository);
    }

    @Test
    public void shouldReturnAllEventsListByRoom() {

        PageImpl<RoomEvent> returnedPage = new PageImpl<>(List.of(mock(RoomEvent.class)));
        PageRequest page = PageRequest.of(0, 5);
        when(repository.findAllByRoomId(anyLong(), any(Pageable.class))).thenReturn(returnedPage);

        Page<RoomEvent> events = service.findAllByRoomId(1L, page);

        verify(repository, times(1)).findAllByRoomId(1L, page);
        assertEquals(events.getNumberOfElements(), 1);
        assertEquals(events.getTotalPages(), 1);
    }

    @Test
    public void shouldReturnAllEventsByPage() {

        PageImpl<RoomEvent> returnedPage = new PageImpl<>(List.of(mock(RoomEvent.class)));
        when(repository.findAll(any(Pageable.class))).thenReturn(returnedPage);

        PageRequest page = PageRequest.of(0, 5);

        Page<RoomEvent> events = service.findAll(page);

        verify(repository, times(1)).findAll(page);
        assertEquals(events.getNumberOfElements(), 1);
        assertEquals(events.getTotalPages(), 1);
    }

    @Test
    public void shouldSaveAnEvent() {

        RoomEvent event = RoomEventFaker.createFakeEvent(1L);
        when(repository.save(any(RoomEvent.class))).thenReturn(event);

        RoomEvent createdEvent = service.save(event);

        verify(repository, times(1)).save(event);
        assertEquals(createdEvent, event);
    }

    @Test
    public void shouldFindAnElementById() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(RoomEventFaker.createFakeEvent(1L)));

        Optional<RoomEvent> event = service.findById(1L);

        verify(repository, times(1)).findById(1L);
        assertTrue(event.isPresent());
    }

    @Test
    public void shouldNotFindAnElementById() {

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<RoomEvent> event = service.findById(1L);

        verify(repository, times(1)).findById(1L);
        assertTrue(event.isEmpty());
    }

}
