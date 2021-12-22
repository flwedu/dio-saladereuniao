package com.digital.one.saladereuniao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.digital.one.saladereuniao.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByRoomId(Long roomId);

}
