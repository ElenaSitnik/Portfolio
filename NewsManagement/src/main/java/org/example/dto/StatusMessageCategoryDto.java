package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class StatusMessageCategoryDto {
    private String message;

    public StatusMessageCategoryDto(Long id) {
        message = "Категория с ID " + id + " не найдена";
    }
}
