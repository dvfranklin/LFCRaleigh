package com.lfcraleigh;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsRepository extends CrudRepository<NewsItem, Integer> {

    List<NewsItem> findAll();
    List<NewsItem> findAllByOrderByIdDesc();
}
