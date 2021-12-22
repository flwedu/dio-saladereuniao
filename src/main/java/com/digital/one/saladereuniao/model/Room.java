package com.digital.one.saladereuniao.model;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    private String description;

    @OneToMany
    @Column(name = "events")
    private List<Event> events = new ArrayList<Event>();

    public RoomDTO toDTO() {
        RoomDTO dto = new RoomDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        return dto;
    }
}
