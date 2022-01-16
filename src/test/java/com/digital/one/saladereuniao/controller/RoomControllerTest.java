package com.digital.one.saladereuniao.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.List;

import com.digital.one.saladereuniao.DTO.RoomDTO;
import com.digital.one.saladereuniao.exception.ResourceNotFoundException;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.service.RoomService;
import com.digital.one.saladereuniao.utils.RoomEventFaker;
import com.digital.one.saladereuniao.utils.RoomFaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
public class RoomControllerTest {

        @MockBean
        private RoomService roomService;

        @Autowired
        private RoomController roomController;

        @BeforeEach
        public void setup() {
                RestAssuredMockMvc.standaloneSetup(this.roomController);
        }

        @Test
        @DisplayName("Should return Sucess (200) HTTP status code when searched for one existing room and response body contains a room")
        public void shouldReturnSucess_WhenSearchedForRoom() throws ResourceNotFoundException {

                Room room = RoomFaker.createFakeRoom(1L);

                Mockito.when(roomService.findRoomByIdOrThrowNotFoundException(Mockito.anyLong())).thenReturn(room);

                RoomDTO responseRoomDTO = RestAssuredMockMvc.given().accept(ContentType.JSON).when()
                                .get("/api/v1/rooms/{id}", 1L).then()
                                .statusCode(HttpStatus.OK.value()).extract().as(RoomDTO.class);

                assertEquals(room.toDTO(), responseRoomDTO);
        }

        @Test
        @DisplayName("Should return Sucess (200) HTTP status code when searched for all rooms list")
        public void shouldReturnSucess_WhenSearchedForAllRoomsList() {

                List<Room> list = List.of(RoomFaker.createFakeRoom(1L));

                Mockito.when(roomService.findAll()).thenReturn(list);

                RestAssuredMockMvc.given().accept(ContentType.JSON).when().get("/api/v1/rooms/").then()
                                .statusCode(HttpStatus.OK.value());

        }

        @Test
        @DisplayName("Should return Not Found (404) HTTP status code when don't found a room resource")
        public void shouldReturnNotFound_WhenSearchedForANonexistentRoom() throws ResourceNotFoundException {

                Mockito.when(roomService.findRoomByIdOrThrowNotFoundException(Mockito.anyLong()))
                                .thenThrow(new ResourceNotFoundException("Room not found"));

                RestAssuredMockMvc.given().accept(ContentType.JSON).when().get("/api/v1/rooms/{id}", 1L).then()
                                .statusCode(HttpStatus.NOT_FOUND.value());

        }

        @Test
        @DisplayName("Should return Sucess (200) HTTP status code when creating a room and the response body contais the room DTO")
        public void shouldReturnCreated_WhenCreatingARoom() {

                Room room = RoomFaker.createFakeRoom(1L);
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

        @ParameterizedTest
        @ValueSource(longs = { 1L, 2L, 3L })
        @DisplayName("Should return Accepted (202) when updating a room")
        public void shouldReturnAccepted_WhenUpdatingARoom(Long id) throws ResourceNotFoundException {

                Room room = RoomFaker.createFakeRoom(id);
                Mockito.when(roomService.findRoomByIdOrThrowNotFoundException(id)).thenReturn(room);
                Mockito.when(roomService.save(Mockito.any(Room.class))).thenReturn(room);

                RoomDTO responseRoomDto = RestAssuredMockMvc.given()
                                .accept(ContentType.JSON)
                                .contentType(ContentType.JSON)
                                .body(room)
                                .when()
                                .put("/api/v1/rooms/{id}", id)
                                .then()
                                .statusCode(HttpStatus.ACCEPTED.value())
                                .extract()
                                .as(RoomDTO.class);
                assertEquals(responseRoomDto, room.toDTO());
        }

        @Test
        @DisplayName("Should return Not Found (404) when updating a nonexistent room")
        public void shouldReturnNotFound_WhenUpdatingANonExistentRoom() throws ResourceNotFoundException {

                Mockito.when(roomService.findRoomByIdOrThrowNotFoundException(Mockito.anyLong()))
                                .thenThrow(new ResourceNotFoundException("Room not found"));

                RestAssuredMockMvc.given()
                                .accept(ContentType.JSON)
                                .contentType(ContentType.JSON)
                                .body(RoomFaker.createFakeRoomDto(1L))
                                .when()
                                .put("/api/v1/rooms/{id}", 1L)
                                .then()
                                .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        @DisplayName("Should return Accepted (202) when deleting a room")
        public void shouldReturnAccepted_WhenDeletingARoom() throws ResourceNotFoundException {

                Mockito.when(roomService.findRoomByIdOrThrowNotFoundException(Mockito.anyLong()))
                                .thenReturn(Mockito.mock(Room.class));

                RestAssuredMockMvc.given()
                                .accept(ContentType.JSON)
                                .when()
                                .delete("/api/v1/rooms/{id}", 1L)
                                .then()
                                .statusCode(HttpStatus.ACCEPTED.value());
        }

        @Test
        @DisplayName("Should return NotFound (404) when deleting a room nonexistent room")
        public void shouldReturnNotFound_WhenDeletingANonExistentRoom() throws ResourceNotFoundException {

                Mockito.when(roomService.findRoomByIdOrThrowNotFoundException(Mockito.anyLong()))
                                .thenThrow(new ResourceNotFoundException("Room not found"));

                RestAssuredMockMvc.given()
                                .accept(ContentType.JSON)
                                .when()
                                .delete("/api/v1/rooms/{id}", 1L)
                                .then()
                                .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        public void shouldReturnSucess_WhenRequestingAllEventsByRoom() throws ResourceNotFoundException {

                Room room = RoomFaker.createFakeRoom(1L);
                List<RoomEvent> events = List.of(RoomEventFaker.createFakeEvent(1L),
                                RoomEventFaker.createFakeEvent(2L));
                events.forEach(event -> event.setRoom(room));
                room.setEvents(events);

                Mockito.when(roomService.findRoomByIdOrThrowNotFoundException(anyLong())).thenReturn(room);

                RestAssuredMockMvc.given()
                                .accept(ContentType.JSON)
                                .when()
                                .get("api/v1/rooms/{id}/events", 1L)
                                .then()
                                .statusCode(HttpStatus.OK.value());
        }

}
