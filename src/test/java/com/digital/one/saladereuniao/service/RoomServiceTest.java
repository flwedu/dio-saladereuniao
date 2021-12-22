package com.digital.one.saladereuniao.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.repository.RoomRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

public class RoomServiceTest {

    private static RoomRepository repository;
    private static RoomService service;

    @BeforeAll
    public static void setup() {

        repository = Mockito.mock(RoomRepository.class);
        service = new RoomService(repository);
    }

    @Test
    @DisplayName("Should return a list with all mocked rooms")
    void shouldReturnAListWithAllRooms() {

        List<Room> mockList = Arrays.asList(Mockito.mock(Room.class));

        Mockito.when(repository.findAll()).thenReturn(mockList);

        List<Room> returnedList = service.findAll();
        assertFalse(returnedList.isEmpty());
        assertIterableEquals(returnedList, mockList);
    }

    @DisplayName("Should find a room")
    @ParameterizedTest
    @ValueSource(longs = { 1L, 2L, 3L })
    void shouldFindAnElement(Long id) {

        Room room = Mockito.mock(Room.class);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(room));

        Optional<Room> findedInService = service.findById(id);

        Assertions.assertEquals(findedInService.get(), room);

    }

    @DisplayName("Should not find any element")
    @ParameterizedTest
    @ValueSource(longs = { 4L, 5L, 6L })
    void shouldNotFindAnElement(Long id) {

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Optional<Room> findedInService = service.findById(id);

        Assertions.assertTrue(findedInService.isEmpty());
    }
}
