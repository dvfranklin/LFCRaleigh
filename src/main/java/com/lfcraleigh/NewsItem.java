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
}
