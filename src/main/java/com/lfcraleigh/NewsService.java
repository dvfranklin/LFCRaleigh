package com.lfcraleigh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    NewsRepository newsRepo;

    public List<NewsItem> getAllNews(){
        return newsRepo.findAllByOrderByIdDesc();
    }

    public NewsItem getSelectedNews(int id){
        return newsRepo.findOne(id);
    }

    public void deleteSelectedNews(int id){
        newsRepo.delete(id);
    }
}
