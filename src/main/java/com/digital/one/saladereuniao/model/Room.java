package com.digital.one.saladereuniao.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.digital.one.saladereuniao.DTO.RoomDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @OneToMany
    @Column(name = "events")
    private List<Event> events = new ArrayList<Event>();

    public Room(Long id, String name, LocalDate reservationDate, LocalTime startHour, LocalTime endHour) {
        this.id = id;
        this.name = name;
    }

    public RoomDTO toDTO() {
        RoomDTO dto = new RoomDTO();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}
