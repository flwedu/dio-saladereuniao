package com.digital.one.saladereuniao.repository;

import java.util.List;

import com.digital.one.saladereuniao.model.Event;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    List<Event> findAllByRoomId(Long roomId);

}
