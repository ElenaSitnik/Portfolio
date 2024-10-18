package org.example.controllers;

import org.example.dto.NewsDto;
import org.example.dto.StatusMessageNewsDto;
import org.example.services.CategoryCRUDService;
import org.example.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsCRUDService newsService;
    private final CategoryCRUDService categoryService;
    public NewsController(NewsCRUDService newsService, CategoryCRUDService categoryService) {
        this.newsService = newsService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getNewsById(@PathVariable Long id) {
        if (newsService.getById(id) == null) {
            return new ResponseEntity<>(new StatusMessageNewsDto(id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newsService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllNews() {
        return new ResponseEntity<>(newsService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createNews(@RequestBody NewsDto news) {
        newsService.create(news);
        return new ResponseEntity<>(news, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateNews(@PathVariable Long id, @RequestBody NewsDto news) {
        news.setId(id);
        newsService.update(news);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNews(@PathVariable Long id) {
        if (newsService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new StatusMessageNewsDto(id), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity getAllNews(@PathVariable Long id) {
        Collection<NewsDto> list =  categoryService.getNews(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
