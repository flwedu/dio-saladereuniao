package com.digital.one.saladereuniao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.digital.one.saladereuniao.DTO.RoomEventDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roomevents")
public class RoomEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    Room room;

    @Column(nullable = false)
    private LocalDateTime startingTime;

    @Column(nullable = false)
    private LocalDateTime endingTime;

    public RoomEventDTO toDto() {
        RoomEventDTO dto = new RoomEventDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setStartingTime(startingTime);
        dto.setEndingTime(endingTime);
        dto.setRoomId(room.getId());
        return dto;
    }
}
