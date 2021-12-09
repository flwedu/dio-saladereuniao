package com.digital.one.saladereuniao.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.digital.one.saladereuniao.model.Room;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomMapperTest {

    private static Room expectedRoom = new Room(
            1L, "Test", LocalDate.of(2021, 12, 01), LocalTime.of(11, 00, 00),
            LocalTime.of(10, 00, 00));

    private static RoomDTO expectedRoomDto = new RoomDTO(
            1L, "Test", LocalDate.of(2021, 12, 01),
            LocalTime.of(11, 00, 00),
            LocalTime.of(10, 00, 00));

    @Test
    @DisplayName("An mapper should convert from Entity to DTO")
    void shouldConvertFromEntityToDto() {

        RoomDTO dto = RoomMapper.toDTO(expectedRoom);
        assertEquals(dto, expectedRoomDto);
    }

    @Test
    @DisplayName("An mapper should convert from DTO to Entity")
    void shouldConvertFromDtoToEntity() {

        Room testRoom = RoomMapper.toEntity(expectedRoomDto);
        assertEquals(testRoom, expectedRoom);
    }

    @Test
    @DisplayName("Should convert a List of Entities to a List of DTO's")
    void shouldConvertAnListOfEntitiesToDto() {

        List<Room> roomsList = List.of(
                new Room(1L, "Test", LocalDate.of(2021, 12, 01), LocalTime.of(11, 00, 00),
                        LocalTime.of(10, 00, 00)),
                new Room(2L, "Test", LocalDate.of(2021, 12, 01), LocalTime.of(11, 00, 00),
                        LocalTime.of(10, 00, 00)),
                new Room(3L, "Test", LocalDate.of(2021, 12, 01), LocalTime.of(11, 00, 00),
                        LocalTime.of(10, 00, 00)));

        List<RoomDTO> expectedRoomDtoList = List.of(
                new RoomDTO(1L, "Test", LocalDate.of(2021, 12, 01), LocalTime.of(11, 00, 00),
                        LocalTime.of(10, 00, 00)),
                new RoomDTO(2L, "Test", LocalDate.of(2021, 12, 01), LocalTime.of(11, 00, 00),
                        LocalTime.of(10, 00, 00)),
                new RoomDTO(3L, "Test", LocalDate.of(2021, 12, 01), LocalTime.of(11, 00, 00),
                        LocalTime.of(10, 00, 00)));

        List<RoomDTO> convertedRoomDto = roomsList.stream().map(RoomMapper::toDTO).collect(Collectors.toList());

        assertIterableEquals(expectedRoomDtoList, convertedRoomDto);
    }

}
