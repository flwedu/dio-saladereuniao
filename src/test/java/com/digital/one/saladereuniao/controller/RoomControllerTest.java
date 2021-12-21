package com.digital.one.saladereuniao.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.digital.one.saladereuniao.DTO.RoomDTO;
import com.digital.one.saladereuniao.controler.RoomController;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.service.RoomService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest
public class RoomControllerTest {

        @Autowired
        private RoomController roomController;

        @MockBean
        private RoomService roomService;

        @BeforeEach
        public void setup() {
                RestAssuredMockMvc.standaloneSetup(this.roomController);
        }

        @Test
        @DisplayName("Should return Sucess (200) HTTP status code when searched for one existing room and body contais room")
        public void shouldReturnSucess_WhenSearchedForRoom() {

                Optional<Room> room = Optional
                                .of(new Room(1L, "Room 1", LocalDate.now().plusDays(2), LocalTime.of(10, 0),
                                                LocalTime.NOON));

                Mockito.when(roomService.findById(1L)).thenReturn(room);

                RoomDTO responseRoomDTO = RestAssuredMockMvc.given().accept(ContentType.JSON).when()
                                .get("/api/v1/rooms/{id}", 1L).then()
                                .statusCode(HttpStatus.OK.value()).extract().as(RoomDTO.class);

                assertEquals(room.get().toDTO(), responseRoomDTO);
        }

        @Test
        @DisplayName("Should return Sucess (200) HTTP status code when searched for all rooms list")
        public void shouldReturnSucess_WhenSearchedForAllRoomsList() {

                List<Room> list = List.of(new Room(1L, "Room 1", LocalDate.now().plusDays(2), LocalTime.of(10, 0),
                                LocalTime.NOON));

                Mockito.when(roomService.findAll()).thenReturn(list);

                RestAssuredMockMvc.given().accept(ContentType.JSON).when().get("/api/v1/rooms/").then()
                                .statusCode(HttpStatus.OK.value());

        }

        @Test
        @DisplayName("Should return Not Found (404) HTTP status code when don't found a room resource")
        public void shouldReturnNotFound_WhenSearchedForANonexistentRoom() {

                Optional<Room> notARoom = Optional.empty();
                Mockito.when(roomService.findById(1L)).thenReturn(notARoom);

                RestAssuredMockMvc.given().accept(ContentType.JSON).when().get("/api/v1/rooms/{id}", 1L).then()
                                .statusCode(HttpStatus.NOT_FOUND.value());

        }

        @Test
        @DisplayName("Should return Sucess (200) HTTP status code when creating a room")
        public void shouldReturnCreated_WhenCreatingARoom() {

                Room room = new Room(1L, "Room 1", LocalDate.now().plusDays(2), LocalTime.of(10, 0),
                                LocalTime.NOON);
                Mockito.when(roomService.save(Mockito.any())).thenReturn(room);

                RestAssuredMockMvc
                                .given()
                                .accept(ContentType.JSON)
                                .contentType(ContentType.JSON)
                                .body(room)
                                .when()
                                .post("/api/v1/rooms/")
                                .then()
                                .statusCode(HttpStatus.OK.value());
        }

}
