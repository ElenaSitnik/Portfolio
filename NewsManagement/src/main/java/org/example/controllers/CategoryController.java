package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDto;
import org.example.dto.StatusMessageCategoryDto;
import org.example.services.CategoryCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryCRUDService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity getCategoryById(@PathVariable Long id) {
        if (categoryService.getById(id) == null) {
            return new ResponseEntity<>(new StatusMessageCategoryDto(id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllCategories() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createCategory(@RequestBody CategoryDto category) {
        categoryService.create(category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateCategory(@RequestBody CategoryDto category) {
        categoryService.update(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategoryById(@PathVariable Long id) {
        if (categoryService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new StatusMessageCategoryDto(id), HttpStatus.NOT_FOUND);
    }
}
