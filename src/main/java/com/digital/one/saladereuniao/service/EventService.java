package com.digital.one.saladereuniao.service;

import com.digital.one.saladereuniao.model.Event;
import com.digital.one.saladereuniao.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class EventService {

    private EventRepository repository;

    public EventService(@Autowired EventRepository repository) {
        this.repository = repository;
    }

    public Page<Event> findAllByRoomId(Long roomId, Pageable pageable) {
        return this.repository.findAllByRoomId(roomId, pageable);
    }

    public Page<Event> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

}
