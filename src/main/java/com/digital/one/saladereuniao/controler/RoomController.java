package com.digital.one.saladereuniao.controler;

import java.util.List;

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

import com.digital.one.saladereuniao.exception.ResourceNotFoundException;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.service.RoomService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping()
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) throws ResourceNotFoundException {
        Room room = findRoom(id);
        return ResponseEntity.ok(room);
    }

    @PostMapping()
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room newRoom) {
        return ResponseEntity.ok(roomService.save(newRoom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Valid @RequestBody Room newRoomData)
            throws ResourceNotFoundException {
        Room room = findRoom(id);
        room.setName(newRoomData.getName());
        room.setDate(newRoomData.getDate());
        room.setStartHour(newRoomData.getStartHour());
        room.setEndHour(newRoomData.getEndHour());
        return ResponseEntity.ok(roomService.save(room));
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
