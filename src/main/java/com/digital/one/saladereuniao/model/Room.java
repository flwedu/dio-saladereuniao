package com.digital.one.saladereuniao.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @NotNull
    @Length(min = 2, max = 25)
    private String name;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate reservationDate;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startHour;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endHour;

    @Override
    public String toString() {
	return "Room{" + "id=" + id + ", name='" + name + '\'' + ", date='" + reservationDate + '\'' + ", startHour='"
		+ startHour + '\'' + ", endHour='" + endHour + '\'' + '}';
    }

}
