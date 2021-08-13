package com.digital.one.saladereuniao.controler;

import com.digital.one.saladereuniao.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1")
public class RoomController {

    @Autowired
    private RoomService roomService;
}
