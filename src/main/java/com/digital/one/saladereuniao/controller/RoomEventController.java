package com.digital.one.saladereuniao.controller;

import java.net.URI;

import javax.validation.Valid;

import com.digital.one.saladereuniao.DTO.RoomEventDTO;
import com.digital.one.saladereuniao.exception.ResourceNotFoundException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "api/v1")
@Api(value = "RoomEvent")
public class RoomEventController {

    private RoomEventService eventService;

    private RoomService roomService;

    @Autowired
    public RoomEventController(RoomEventService eventService, RoomService roomService) {
        this.eventService = eventService;
        this.roomService = roomService;
    }

    @GetMapping("/events")
    @ApiOperation(value = "Show all room events by page number")
    public ResponseEntity<Page<RoomEventDTO>> getAllEvents(@RequestParam(defaultValue = "0") Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<RoomEvent> events = eventService.findAll(pageRequest);
        return ResponseEntity.ok().body(events.map(RoomEvent::toDto));
    }

    @GetMapping("/events/{id}")
    @ApiOperation(value = "Show a room event with the specified Id")
    public ResponseEntity<RoomEventDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {

        RoomEvent event = eventService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Event id %s not found", id)));
        return ResponseEntity.ok(event.toDto());
    }

    @PostMapping("/events")
    @ApiOperation(value = "Create a room Event with data in Request Body")
    public ResponseEntity<String> save(UriComponentsBuilder uriBuilder, @Valid @RequestBody RoomEventDTO newEvent)
            throws ResourceNotFoundException {

        roomService.findRoomByIdOrThrowNotFoundException(newEvent.getRoomId());
        RoomEvent eventToSave = newEvent.toEntity();

        RoomEvent savedEvent = eventService.save(eventToSave);

        URI uri = uriBuilder.path("api/v1/events/{id}").buildAndExpand(savedEvent.getId().toString()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
