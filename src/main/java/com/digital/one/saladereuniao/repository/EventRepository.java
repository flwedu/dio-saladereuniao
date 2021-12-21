package com.digital.one.saladereuniao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.digital.one.saladereuniao.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
