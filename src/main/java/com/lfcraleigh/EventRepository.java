package com.lfcraleigh;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {

    List<Event> findAll();
    List<Event> findAllByOrderByIdDesc();
}
