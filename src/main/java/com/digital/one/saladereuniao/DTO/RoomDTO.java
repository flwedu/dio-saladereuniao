package com.digital.one.saladereuniao.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.digital.one.saladereuniao.model.Room;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RoomDTO {

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

    public Room toEntity() {
        Room room = new Room();
        room.setId(id);
        room.setName(name);
        return room;
    }

}
