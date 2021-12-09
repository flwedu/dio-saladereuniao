package com.digital.one.saladereuniao.DTO;

import com.digital.one.saladereuniao.model.Room;

public class RoomMapper {

    private RoomMapper() {
    }

    public static Room toEntity(RoomDTO dto) {
        return dto.toEntity();
    }

    public static RoomDTO toDTO(Room room) {
        return room.toDTO();
    }

}
