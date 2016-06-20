package com.lfcraleigh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepo;

    public List<Event> getAllEvents(){
        return eventRepo.findAllByOrderByIdDesc();
    }

    public Event getSelectedEvent(int id){
        return eventRepo.findOne(id);
    }

    public void deleteSelectedEvent(int id){
        eventRepo.delete(id);
    }

}
