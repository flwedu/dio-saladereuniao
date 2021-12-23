package com.digital.one.saladereuniao.controler;

import com.digital.one.saladereuniao.model.Event;
import com.digital.one.saladereuniao.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/events")
public class EventController {

    private EventService eventService;

    public EventController(@Autowired EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Page<Event>> getAllEvents(@PathVariable("pageNumber") int pageNumber) {

        PageRequest page = PageRequest.of(pageNumber, 10);
        return ResponseEntity.ok().body(eventService.findAll(page));
    }

}
