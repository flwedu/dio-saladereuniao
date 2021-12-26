package com.digital.one.saladereuniao.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.digital.one.saladereuniao.DTO.RoomEventDTO;
import com.digital.one.saladereuniao.controler.RoomEventController;
import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.service.RoomEventService;
import com.digital.one.saladereuniao.service.RoomService;
import com.digital.one.saladereuniao.utils.RoomEventFaker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Testing with a diferent approach of RoomControllerTest.
 * Here is used the @WebMvcTest annotation to test only the web layer, and the
 * MockMvcRequestBuilders and MockMvcResultMatchers to build the test.
 */
@WebMvcTest(RoomEventController.class)
public class RoomEventControllerTest {

    private static String baseUrl = "api/v1/events";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomEventService eventService;

    @MockBean
    private RoomService roomService;

    @Test
    public void shouldReturnSucess_WhenRequestingAllRoms() {

        Page<RoomEvent> responsePage = new PageImpl<>(List.of(mock(RoomEvent.class)));
        when(eventService.findAll(any(Pageable.class))).thenReturn(responsePage);

        try {
            mockMvc.perform(get(baseUrl + "?page=0")).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnCreated_WhenSavingAnEvent() {

        Long roomEventId = 1L;

        RoomEventDTO dto = RoomEventFaker.createFakeEvent(roomEventId).toDto();
        when(eventService.save(any(RoomEvent.class))).thenReturn(dto.toEntity());

        try {
            mockMvc.perform(post("api/v1/events")).andExpect(status().isOk())
                    .andExpect(content().string(contains(dto.toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
