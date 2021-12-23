package com.digital.one.saladereuniao.controller;

import static org.mockito.Mockito.*;

import java.util.List;

import com.digital.one.saladereuniao.controler.EventController;
import com.digital.one.saladereuniao.model.Event;
import com.digital.one.saladereuniao.service.EventService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest
public class EventControllerTest {

    private static String baseUrl = "api/v1/events";

    @Autowired
    private EventController eventController;

    @MockBean
    private EventService eventService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(this.eventController);
    }

    @Test
    public void shouldReturnSucess_WhenRequestingAllRoms() {

        Page<Event> responsePage = new PageImpl<>(List.of(mock(Event.class)));
        when(eventService.findAll(any(Pageable.class))).thenReturn(responsePage);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                .get(baseUrl + "/page/{pageNumber}", 1)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
