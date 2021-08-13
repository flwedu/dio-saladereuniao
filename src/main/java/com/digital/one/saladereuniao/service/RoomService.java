package com.digital.one.saladereuniao.service;

import com.digital.one.saladereuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
}
