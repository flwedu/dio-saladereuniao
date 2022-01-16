package com.digital.one.saladereuniao.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.digital.one.saladereuniao.DTO.RoomDTO;
import com.digital.one.saladereuniao.DTO.RoomEventDTO;
import com.digital.one.saladereuniao.exception.ResourceNotFoundException;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("api/v1/rooms")
@Api(value = "Room")
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    @ApiOperation(value = "Show all rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.findAll().stream().map(Room::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Show a room with the specified Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource with Id finded"),
            @ApiResponse(code = 404, message = "Resource with Id not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) throws ResourceNotFoundException {
        Room room = roomService.findRoomByIdOrThrowNotFoundException(id);
        return ResponseEntity.ok(room.toDTO());
    }

    @GetMapping("/{id}/events")
    @ApiOperation(value = "Show all events of the room with the specified Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource with Id finded"),
            @ApiResponse(code = 404, message = "Resource with Id not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<Page<RoomEventDTO>> getEventsByRoomById(@PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page) throws ResourceNotFoundException {

        Room room = roomService.findRoomByIdOrThrowNotFoundException(id);

        PageRequest pageConfig = PageRequest.of(page, 10);

        Page<RoomEvent> eventsByRoomid = new PageImpl<>(room.getEvents(), pageConfig, room.getEvents().size());

        return ResponseEntity.ok(eventsByRoomid.map(RoomEvent::toDto));
    }

    @PostMapping()
    @ApiOperation(value = "Create a room with the data in Request Body")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource created"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO room) {
        Room savedRoom = roomService.save(room.toEntity());
        return ResponseEntity.ok(savedRoom.toDTO());
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resource updated"),
            @ApiResponse(code = 404, message = "Resource with Id not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @ApiOperation(value = "Updates a room with Id in URL with the data in Request Body")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDTO newRoomData)
            throws ResourceNotFoundException {
        roomService.findRoomByIdOrThrowNotFoundException(id);
        newRoomData.setId(id);
        Room updatedRoom = roomService.save(newRoomData.toEntity());
        return new ResponseEntity<RoomDTO>(updatedRoom.toDTO(), HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a Room with specified Id")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Operation accepted"),
            @ApiResponse(code = 404, message = "Resource with Id not found"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<?> deleteRoomById(@PathVariable Long id) throws ResourceNotFoundException {
        roomService.findRoomByIdOrThrowNotFoundException(id);
        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
