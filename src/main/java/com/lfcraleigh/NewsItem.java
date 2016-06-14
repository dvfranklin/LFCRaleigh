package com.lfcraleigh;

import javax.persistence.*;

@Entity
public class NewsItem {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String body;

    public NewsItem() {
    }

    public NewsItem(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
