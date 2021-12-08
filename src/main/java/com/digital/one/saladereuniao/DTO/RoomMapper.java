package com.digital.one.saladereuniao.DTO;

import com.digital.one.saladereuniao.model.Room;

public class RoomMapper {

    static public Room toEntity(RoomDTO dto) {
	return dto.toEntity();
    }

    static public RoomDTO toDTO(Room room) {
	return room.toDTO();
    }

}
