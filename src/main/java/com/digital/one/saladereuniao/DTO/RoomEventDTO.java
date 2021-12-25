package com.digital.one.saladereuniao.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.digital.one.saladereuniao.model.RoomEvent;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class RoomEventDTO {

    private Long id;

    @NotNull
    @Length(max = 50)
    private String name;

    @Length(max = 100)
    private String description;

    @NotNull
    private Long roomId;

    private LocalDateTime startingTime;

    private LocalDateTime endingTime;

    public RoomEvent toEntity() {

        RoomEvent event = new RoomEvent();
        event.setId(id);
        event.setName(name);
        event.setDescription(description);
        event.setStartingTime(startingTime);
        event.setEndingTime(endingTime);
        return event;
    }
}
