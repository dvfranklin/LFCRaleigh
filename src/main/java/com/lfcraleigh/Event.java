package com.lfcraleigh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Event {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String dateTime;

    @Column(nullable = false)
    String location;

    public Event() {
    }

    public Event(String description, String dateTime, String location) {
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
