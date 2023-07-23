package ru.practicum.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestCommentDto {
    @NotBlank(message = "Комментарий не может быть пустым.")
    @Size(max = 200, message = "Комментарий не должен быть больше 200")
    String text;
}
