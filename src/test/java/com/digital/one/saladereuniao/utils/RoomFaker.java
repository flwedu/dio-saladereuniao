package com.digital.one.saladereuniao.utils;

import java.util.List;

import com.digital.one.saladereuniao.DTO.RoomDTO;
import com.digital.one.saladereuniao.model.Room;

public class RoomFaker {

    public static Room createFakeRoom(Long id) {
        return new Room(id, "Room 1", "An test room", List.of());
    }

    public static RoomDTO createFakeRoomDto(Long id) {
        return new RoomDTO(id, "Room 1", "An test room", null);
    }

}
