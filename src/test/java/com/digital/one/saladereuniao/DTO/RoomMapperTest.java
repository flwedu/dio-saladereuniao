package com.digital.one.saladereuniao.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import java.util.stream.Collectors;

import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.utils.RoomFaker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomMapperTest {

        @Test
        @DisplayName("An mapper should convert from Entity to DTO")
        void shouldConvertFromEntityToDto() {

                Room room = RoomFaker.createFakeRoom(1L);

                RoomDTO dto = RoomMapper.toDTO(room);
                assertEquals(dto, room.toDTO());
        }

        @Test
        @DisplayName("An mapper should convert from DTO to Entity")
        void shouldConvertFromDtoToEntity() {

                RoomDTO dto = RoomFaker.createFakeRoomDto(1L);

                Room room = RoomMapper.toEntity(dto);
                assertEquals(room, dto.toEntity());
        }

        @Test
        @DisplayName("Should convert a List of Entities to a List of DTO's")
        void shouldConvertAnListOfEntitiesToDto() {

                List<Room> roomsList = List.of(RoomFaker.createFakeRoom(1L), RoomFaker.createFakeRoom(2L));

                List<RoomDTO> expectedRoomDtoList = List.of(RoomFaker.createFakeRoomDto(1L),
                                RoomFaker.createFakeRoomDto(2L));

                List<RoomDTO> convertedRoomDto = roomsList.stream().map(RoomMapper::toDTO).collect(Collectors.toList());

                assertIterableEquals(expectedRoomDtoList, convertedRoomDto);
        }
}
