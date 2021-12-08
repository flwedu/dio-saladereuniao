package com.digital.one.saladereuniao.DTO;

import com.digital.one.saladereuniao.model.Room;

public class RoomMapper {

    static Room toEntity(RoomDTO dto) {
        return new Room(
                dto.getId(),
                dto.getName(),
                dto.getReservationDate(),
                dto.getStartHour(),
                dto.getEndHour());
    }

    static RoomDTO toDTO(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getReservationDate(),
                room.getStartHour(),
                room.getEndHour());
    }

}
