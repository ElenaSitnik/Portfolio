package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusMessageNewsDto {
    private String message;

    public StatusMessageNewsDto(Long id) {
        message = "Новость с ID " + id + " не найдена";
    }

}
