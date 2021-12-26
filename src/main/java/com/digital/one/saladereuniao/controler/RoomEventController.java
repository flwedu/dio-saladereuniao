package com.digital.one.saladereuniao.controler;

import javax.validation.Valid;

import com.digital.one.saladereuniao.DTO.RoomEventDTO;
import com.digital.one.saladereuniao.exception.ResourceNotFoundException;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.service.RoomEventService;
import com.digital.one.saladereuniao.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class RoomEventController {

    private RoomEventService eventService;

    private RoomService roomService;

    @Autowired
    public RoomEventController(RoomEventService eventService, RoomService roomService) {
        this.eventService = eventService;
        this.roomService = roomService;
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

    @PostMapping("/events")
    public ResponseEntity<RoomEvent> save(@RequestBody @Valid RoomEventDTO newEvent) throws ResourceNotFoundException {

        Room room = roomService.findRoomByIdOrThrowNotFoundException(newEvent.getRoomId());
        RoomEvent eventToSave = newEvent.toEntity();
        eventToSave.setRoom(room);

        return ResponseEntity.ok().body(eventService.save(eventToSave));
    }

}
