package com.digital.one.saladereuniao.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

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
public class EventDto {

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
}
