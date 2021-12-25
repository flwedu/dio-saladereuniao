package com.digital.one.saladereuniao.controler;

import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.service.RoomEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class RoomEventController {

    private RoomEventService eventService;

    @Autowired
    public RoomEventController(RoomEventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/page/{pageNumber}")
    public ResponseEntity<Page<RoomEvent>> getAllEvents(@PathVariable("pageNumber") int pageNumber) {

        PageRequest page = PageRequest.of(pageNumber, 10);
        return ResponseEntity.ok().body(eventService.findAll(page));
    }

    @GetMapping("/rooms/{roomId}/events/page/{pageNumber}")
    public ResponseEntity<Page<RoomEvent>> getEventsByRoom(@PathVariable("roomId") Long roomId,
            @PathVariable("pageNumber") int pageNumber) {

        PageRequest page = PageRequest.of(pageNumber, 10);

        return ResponseEntity.ok().body(eventService.findAllByRoomId(roomId, page));

    }

}
