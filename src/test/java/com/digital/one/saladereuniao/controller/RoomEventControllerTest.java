package com.digital.one.saladereuniao.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.digital.one.saladereuniao.DTO.RoomEventDTO;
import com.digital.one.saladereuniao.model.Room;
import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.service.RoomEventService;
import com.digital.one.saladereuniao.service.RoomService;
import com.digital.one.saladereuniao.utils.RoomEventFaker;
import com.digital.one.saladereuniao.utils.RoomFaker;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Testing with a diferent approach of RoomControllerTest.
 * Here is used the @WebMvcTest annotation to test only the web layer, and the
 * MockMvcRequestBuilders and MockMvcResultMatchers to build the test.
 */
@WebMvcTest(RoomEventController.class)
public class RoomEventControllerTest {

    private static String baseUrl = "/api/v1/events";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomEventService eventService;

    @MockBean
    private RoomService roomService;

    // This object has the resposibility to convert objects to JSON string format
    static ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void setup() {

        // configuring the datetime format of JSON output string
        mapper.findAndRegisterModules();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"));
    }

    @Test
    @Description("Should return Sucess when requesting all roomEvents with page number in pathVariable, and body contais a page")
    public void shouldReturnSucess_WhenRequestingAllRoomsEvents() throws Exception {

        Page<RoomEvent> responsePage = new PageImpl<>(List.of(mock(RoomEvent.class)));
        doReturn(responsePage).when(eventService).findAll(any(Pageable.class));

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "?page=0"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").value(responsePage.isEmpty()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(responsePage.getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(responsePage.getTotalPages()));

    }

    @Test
    @Description("Should return Sucess when requesting all roomEvents without page number in pathVariable")
    public void shouldReturnSucess_WhenRequestingResourcesWithoutPagePathVariable() throws Exception {

        Page<RoomEvent> responsePage = new PageImpl<>(List.of(mock(RoomEvent.class)));
        doReturn(responsePage).when(eventService).findAll(any(Pageable.class));

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.empty").value(responsePage.isEmpty()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(responsePage.getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(responsePage.getTotalPages()));

    }

    @Test
    @Description("Should return Sucess when requesting a roomEvent by Id, and body contains a roomEvent")
    public void shouldReturnSucess_WhenRequestingARoomEventById() throws Exception {

        RoomEvent roomEvent = RoomEventFaker.createFakeEvent(1L);

        doReturn(Optional.of(roomEvent)).when(eventService).findById(anyLong());

        String expectedResultContent = mapper.writeValueAsString(roomEvent.toDto());
        mockMvc.perform(
                MockMvcRequestBuilders.get(baseUrl + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResultContent));
    }

    @Test
    @Description("Should return Error when requesting an unexisting roomEvent by Id")
    public void shouldReturnError_WhenRequestingResourceById() throws Exception {

        doReturn(Optional.empty()).when(eventService).findById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/1")).andExpect(status().isNotFound());
    }

    @Test
    @Description("Should return BadRequest when requesting with an invalid URL")
    public void shouldReturnBadRequest_WhenRequestingWithInvalidUrl() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/iwue0921")).andExpect(status().isBadRequest());

    }

    @Test
    @Description("Should return Created when saving a room, and header contains the location of created resource")
    public void shouldReturnCreated_WhenSavingAnResource() throws Exception {

        Long roomEventId = 1L;

        Room room = RoomFaker.createFakeRoom(1L);
        RoomEvent roomEvent = RoomEventFaker.createFakeEvent(roomEventId);
        RoomEventDTO roomEventDTO = roomEvent.toDto();
        roomEventDTO.setRoomId(1L);

        doReturn(room).when(roomService).findRoomByIdOrThrowNotFoundException(anyLong());
        doReturn(roomEvent).when(eventService).save(any(RoomEvent.class));
        doReturn(room).when(roomService).save(any(Room.class));

        mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl)
                        .content(asJsonString(
                                roomEventDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(
                        MockMvcResultMatchers.header().exists("location"));

    }

    @Test
    @Description("Should return BadRequest when POST with an empty body")
    public void shouldReturnBadRequest_WhenPOSTwithEmptyBody() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Should return BadRequest when POST with an invalid body")
    public void shouldReturnBadRequest_WhenPOSTwithInvalidBody() throws Exception {

        RoomEventDTO dto = new RoomEventDTO(1L, "", "", null, LocalDateTime.now(),
                LocalDateTime.now().plusHours(1));

        mockMvc.perform(
                MockMvcRequestBuilders.post(baseUrl)
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Should return Accepted when updating a RoomEvent with valid RequestBody")
    public void shouldReturnAccepted_WhenUpdating() throws Exception {

        RoomEvent fakeEvent = RoomEventFaker.createFakeEvent(1L);
        doReturn(fakeEvent).when(eventService).findRoomEventByIdOrThrowNotFoundException(anyLong());
        doReturn(fakeEvent).when(eventService).save(any(RoomEvent.class));

        mockMvc.perform(
                MockMvcRequestBuilders.put(baseUrl + "/1")
                        .content(asJsonString(fakeEvent.toDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    public static String asJsonString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
