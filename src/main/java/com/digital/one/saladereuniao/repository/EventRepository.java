package com.digital.one.saladereuniao.repository;

import java.util.List;

import com.digital.one.saladereuniao.model.Event;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    List<Event> findAllByRoomId(Long roomId);

}
