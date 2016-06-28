package com.lfcraleigh;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class NewsItem {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String body;

    @Column(nullable = false)
    LocalDateTime dateTime;


    @Transient
    String dateTimeString;


    public NewsItem() {
    }

    public NewsItem(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public NewsItem(String title, String body, LocalDateTime dateTime) {
        this.title = title;
        this.body = body;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
