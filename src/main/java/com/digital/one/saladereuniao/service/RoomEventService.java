package com.digital.one.saladereuniao.service;

import java.util.Optional;

import com.digital.one.saladereuniao.model.RoomEvent;
import com.digital.one.saladereuniao.repository.RoomEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomEventService {

    private RoomEventRepository roomEventRepository;

    @Autowired
    public RoomEventService(RoomEventRepository roomEventRepository) {
        this.roomEventRepository = roomEventRepository;
    }

    public Page<RoomEvent> findAll(Pageable pageable) {
        return roomEventRepository.findAll(pageable);
    }

    public Optional<RoomEvent> findById(Long id) {
        return roomEventRepository.findById(id);
    }

    public RoomEvent save(RoomEvent event) {
        return roomEventRepository.save(event);
    }

    public void delete(Long id) {
        roomEventRepository.deleteById(id);
    }

    public Page<RoomEvent> findAllByRoomId(Long id, PageRequest pageConfig) {
        return roomEventRepository.findAllbyRoomId(id, pageConfig);
    }

}
