package com.digital.one.saladereuniao.repository;

import com.digital.one.saladereuniao.model.RoomEvent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomEventRepository extends PagingAndSortingRepository<RoomEvent, Long> {

    Page<RoomEvent> findAllByRoomId(Long roomId, Pageable pageable);

}