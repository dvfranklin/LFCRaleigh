package com.lfcraleigh;

import com.google.gson.Gson;
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

    public void saveEvent(Event event){
        eventRepo.save(event);
    }

    public String getEventsInJson(){
        Gson gson = new Gson();

        List<Event> eventList = eventRepo.findAllByOrderByIdDesc();
        String eventJson = gson.toJson(eventList);

        return eventJson;
    }

}
