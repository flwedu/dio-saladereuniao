package com.digital.one.saladereuniao;

import com.digital.one.saladereuniao.controller.RoomController;
import com.digital.one.saladereuniao.controller.RoomEventController;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SaladereuniaoApplicationTests {

	@Autowired
	private RoomController roomController;

	@Autowired
	private RoomEventController eventRoomController;

	@Test
	void contextLoads() {
		Assertions.assertThat(roomController).isNotNull();
		Assertions.assertThat(eventRoomController).isNotNull();
	}

}
