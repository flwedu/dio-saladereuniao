package com.digital.one.saladereuniao.controler;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.one.saladereuniao.DTO.RoomDTO;
import com.digital.one.saladereuniao.exception.ResourceNotFoundException;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.service.RoomService;

@RestController
@RequestMapping("api/v1/rooms")
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.findAll().stream().map(Room::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) throws ResourceNotFoundException {
        Room room = findRoom(id);
        return ResponseEntity.ok(room.toDTO());
    }

    @PostMapping()
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO room) {
        Room savedRoom = roomService.save(room.toEntity());
        return ResponseEntity.ok(savedRoom.toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDTO newRoomData)
            throws ResourceNotFoundException {
        findRoom(id);
        newRoomData.setId(id);
        Room updatedRoom = roomService.save(newRoomData.toEntity());
        return new ResponseEntity<RoomDTO>(updatedRoom.toDTO(), HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomById(@PathVariable Long id) throws ResourceNotFoundException {
        findRoom(id);
        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private Room findRoom(Long id) throws ResourceNotFoundException {
        return roomService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room " + id + " not found"));
    }
}
