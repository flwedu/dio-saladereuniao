package com.digital.one.saladereuniao.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.digital.one.saladereuniao.DTO.RoomDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "start_hour")
    private LocalTime startHour;

    @Column(name = "end_hour")
    private LocalTime endHour;

    public RoomDTO toDTO() {
	RoomDTO dto = new RoomDTO();
	dto.setId(id);
	dto.setName(name);
	dto.setReservationDate(reservationDate);
	dto.setStartHour(startHour);
	dto.setEndHour(endHour);
	return dto;
    }

    @Override
    public String toString() {
	return "Room{" + "id=" + id + ", name='" + name + '\'' + ", date='" + reservationDate + '\'' + ", startHour='"
		+ startHour + '\'' + ", endHour='" + endHour + '\'' + '}';
    }
}
