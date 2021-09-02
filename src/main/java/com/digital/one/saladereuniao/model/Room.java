package com.digital.one.saladereuniao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meetingrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(min = 2, max = 25)
    private String name;

    @NotNull
    private String date;

    @NotNull
    private String startHour;

    @NotNull
    private String endHour;

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name='" + name + '\'' + ", date='" + date + '\'' + ", startHour='" + startHour
                + '\'' + ", endHour='" + endHour + '\'' + '}';
    }
}
