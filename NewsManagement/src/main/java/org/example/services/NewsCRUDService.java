package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dto.NewsDto;
import org.example.entity.Category;
import org.example.entity.News;
import org.example.repositories.CategoryRepository;
import org.example.repositories.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class NewsCRUDService implements CRUDService<NewsDto> {
    private final NewsRepository newsRepo;
    private final CategoryRepository categoryRepo;

    @Override
    public NewsDto getById(Long id) {
        if (newsRepo.findById(id).isEmpty()) {
            return null;
        }
        return mapToDto(newsRepo.findById(id).get());
    }

    @Override
    public Collection<NewsDto> getAll() {
        return newsRepo.findAll().stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(NewsDto item) {
        News news = mapToEntity(item);
        Collection<Category> categories = categoryRepo.findAll();
        for (Category category : categories) {
            if (category.getTitle().equals(item.getCategory())){
                news.setCategory(category);
            }
        }
        newsRepo.save(news);
    }

    @Override
    public void update(NewsDto item) {
        News news = mapToEntity(item);
        Long id = item.getId();
        Category cat = newsRepo.findById(id).get().getCategory();

        Collection<Category> categories = categoryRepo.findAll();
        for (Category category : categories) {
            if (category.getTitle().equals(item.getCategory())) {
                news.setCategory(category);
            } else {
                news.setCategory(cat);
            }
        }
        newsRepo.save(news);
    }

    @Override
    public boolean delete(Long id) {
        if (newsRepo.existsById(id)) {
            newsRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public static NewsDto mapToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setText(news.getText());
        newsDto.setCategory(news.getCategory().getTitle());
        return newsDto;
    }

    public static News mapToEntity(NewsDto newsDto) {
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        return news;

    }
}
