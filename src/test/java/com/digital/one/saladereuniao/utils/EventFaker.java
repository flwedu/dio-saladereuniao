package com.digital.one.saladereuniao.utils;

import java.time.LocalDateTime;

import com.digital.one.saladereuniao.model.Event;
import com.digital.one.saladereuniao.model.Room;

public class EventFaker {

    static public Event createFakeEvent(Long id) {
        Room room = RoomFaker.createFakeRoom(id);
        return new Event(id, "Evento Teste", "Somente um evento Teste", room, LocalDateTime.now(),
                LocalDateTime.now().plusHours(2));
    }

}
