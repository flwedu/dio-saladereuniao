package com.digital.one.saladereuniao.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.repository.RoomRepository;

public class RoomServiceTest {

    private static RoomRepository repository;
    private static RoomService service;

    static List<Room> mockList = Arrays.asList(new Room(1L, "Room 1", "2021-10-09", "10:00", "12:00"),
	    new Room(2L, "Room 2", "2021-10-09", "10:00", "12:00"),
	    new Room(3L, "Room 3", "2021-10-09", "10:00", "12:00"));

    @BeforeAll
    public static void setup() {

	repository = Mockito.mock(RoomRepository.class);
	service = new RoomService(repository);

	Mockito.when(repository.findAll()).thenReturn(mockList);

    }

    @Test
    @DisplayName("Should return a list with all rooms")
    void shouldReturnAListWithAllRooms() {

	List<Room> returnedList = service.findAll();
	Assertions.assertEquals(mockList, returnedList);
    }

    @DisplayName("Should find a room")
    @ParameterizedTest
    @ValueSource(longs = { 1L, 2L, 3L })
    void shouldFindAnElement(Long id) {

	Optional<Room> findedInMock = mockList.stream().filter(room -> room.getId().equals(id)).findFirst();
	Mockito.when(repository.findById(id)).thenReturn(findedInMock);

	Optional<Room> findedInService = service.findById(id);

	Assertions.assertTrue(findedInService.isPresent());
	Assertions.assertEquals(findedInService, findedInMock);

    }

    @DisplayName("Should not find any element")
    @ParameterizedTest
    @ValueSource(longs = { 4L, 5L, 6L })
    void shouldNotFindAnElement(Long id) {

	Optional<Room> findedInMock = mockList.stream().filter(room -> room.getId().equals(id)).findFirst();
	Mockito.when(repository.findById(id)).thenReturn(findedInMock);

	Optional<Room> findedInService = service.findById(id);

	Assertions.assertTrue(findedInService.isEmpty());

    }
}
