package com.digital.one.saladereuniao.controler;

import com.digital.one.saladereuniao.exception.ResourceNotFoundException;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getAllRooms(){
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) throws ResourceNotFoundException{
            Room room = findRoom(id);
            return ResponseEntity.ok(room);
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@Validated @RequestBody Room newRoom){
        return ResponseEntity.ok(roomService.save(newRoom));
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Validated @RequestBody Room newRoomData) throws ResourceNotFoundException {
        Room room = findRoom(id);
        room.setName(newRoomData.getName());
        room.setDate(newRoomData.getDate());
        room.setEndHour(newRoomData.getEndHour());
        return ResponseEntity.ok(roomService.save(room));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRoomById(@PathVariable Long id){
        roomService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    private Room findRoom(Long id) throws ResourceNotFoundException {
        return roomService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room " + id + " not found"));
    }
}
