package com.digital.one.saladereuniao.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.List;
import java.util.Optional;

import com.digital.one.saladereuniao.DTO.RoomDTO;
import com.digital.one.saladereuniao.exception.ResourceNotFoundException;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.service.RoomService;
import com.digital.one.saladereuniao.utils.RoomEventFaker;
import com.digital.one.saladereuniao.utils.RoomFaker;

import org.junit.jupiter.api.Assertions;
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
import io.restassured.module.mockmvc.response.MockMvcResponse;

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

                Room room = RoomFaker.createFakeRoom(1L);
                room.setEvents(null);
                List<Room> list = List.of(room);

                Mockito.when(roomService.findAll()).thenReturn(list);

                Room[] responseArray = RestAssuredMockMvc.given().accept(ContentType.JSON).when().get("/api/v1/rooms/")
                                .then()
                                .statusCode(HttpStatus.OK.value())
                                .extract()
                                .as(Room[].class);

                assertArrayEquals(responseArray, list.toArray());
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
        @DisplayName("Should return Created (201) when saving a room with no Id in body. And response location header is not null")
        public void shouldReturnCreated_WhenCreatingARoom() {

                Room room = RoomFaker.createFakeRoom(1L);
                Mockito.when(roomService.findById(Mockito.anyLong())).thenReturn(Optional.empty());
                Mockito.when(roomService.save(Mockito.any())).thenReturn(room);

                RoomDTO dto = room.toDTO();
                dto.setId(null);

                MockMvcResponse response = RestAssuredMockMvc.given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(dto)
                                .post("/api/v1/rooms/");

                Assertions.assertEquals(HttpStatus.CREATED.value(), response.statusCode());
                Assertions.assertNotNull(response.getHeader("location"));
        }

        @Test
        public void shouldReturnConflict_WhenSavingARoomWithRepeatedId() {

                Room room = RoomFaker.createFakeRoom(1L);
                Mockito.when(roomService.findById(Mockito.anyLong())).thenReturn(Optional.of(room));

                MockMvcResponse response = RestAssuredMockMvc.given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(room.toDTO()).post("/api/v1/rooms");

                Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.statusCode());
        }

        @Test
        @DisplayName("Should return BadRequest (400) when saving a room without body")
        public void shouldReturnBadRequest_WhenCreatingARoomWithoutBody() {

                Room room = RoomFaker.createFakeRoom(1L);
                Mockito.when(roomService.save(Mockito.any())).thenReturn(room);

                MockMvcResponse response = RestAssuredMockMvc.post("/api/v1/rooms/");
                Assertions.assertEquals(response.statusCode(), HttpStatus.BAD_REQUEST.value());
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
