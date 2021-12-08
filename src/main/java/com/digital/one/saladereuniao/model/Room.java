package com.digital.one.saladereuniao.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meetingrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate reservationDate;

    private LocalTime startHour;

    private LocalTime endHour;

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name='" + name + '\'' + ", date='" + reservationDate + '\'' + ", startHour='"
                + startHour + '\'' + ", endHour='" + endHour + '\'' + '}';
    }
}
