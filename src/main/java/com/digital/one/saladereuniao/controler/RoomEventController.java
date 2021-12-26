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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/events")
    public ResponseEntity<Page<RoomEventDTO>> getAllEvents(@RequestParam(defaultValue = "0") Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<RoomEvent> events = eventService.findAll(pageRequest);
        return ResponseEntity.ok().body(events.map(RoomEvent::toDto));
    }

    @PostMapping("/events")
    public ResponseEntity<RoomEventDTO> save(@RequestBody @Valid RoomEventDTO newEvent)
            throws ResourceNotFoundException {

        Room room = roomService.findRoomByIdOrThrowNotFoundException(newEvent.getRoomId());
        RoomEvent eventToSave = newEvent.toEntity();
        eventToSave.setRoom(room);

        RoomEvent savedEvent = eventService.save(eventToSave);

        return ResponseEntity.ok().body(savedEvent.toDto());
    }

}
