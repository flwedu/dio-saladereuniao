package com.digital.one.saladereuniao.controler;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digital.one.saladereuniao.DTO.RoomDTO;
import com.digital.one.saladereuniao.DTO.RoomMapper;
import com.digital.one.saladereuniao.exception.ResourceNotFoundException;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.service.RoomService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;
    private RoomMapper mapper;

    @GetMapping()
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.findAll().stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) throws ResourceNotFoundException {
        Room room = findRoom(id);
        return ResponseEntity.ok(mapper.toDTO(room));
    }

    @PostMapping()
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO room) {
        Room roomToSave = mapper.toEntity(room);
        RoomDTO savedRoom = mapper.toDTO(roomService.save(roomToSave));
        return ResponseEntity.ok(savedRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDTO newRoomData)
            throws ResourceNotFoundException {
        findRoom(id);
        Room room = mapper.toEntity(newRoomData);
        room.setId(id);
        return ResponseEntity.ok(mapper.toDTO(roomService.save(room)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomById(@PathVariable Long id) {
        roomService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private Room findRoom(Long id) throws ResourceNotFoundException {
        return roomService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room " + id + " not found"));
    }
}
