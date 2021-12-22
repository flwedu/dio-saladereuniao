package com.digital.one.saladereuniao.repository;

import com.digital.one.saladereuniao.model.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    Page<Event> findAllByRoomId(Long roomId, Pageable pageable);

}
