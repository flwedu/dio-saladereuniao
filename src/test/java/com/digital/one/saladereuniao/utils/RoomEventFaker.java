package com.digital.one.saladereuniao.utils;

import java.time.LocalDateTime;

import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.model.Room;

public class RoomEventFaker {

    static public RoomEvent createFakeEvent(Long id) {
        Room room = RoomFaker.createFakeRoom(id);
        return new RoomEvent(id, "Evento Teste", "Somente um evento Teste", room, LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
    }

}
