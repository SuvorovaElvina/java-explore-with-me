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
public class UserShortDto {
    Long id;

    @Size(min = 2, max = 250, message = "Имя не может быть меньше 2 и больше 250")
    @NotBlank(message = "Имя не должно быть пустым или null.")
    String name;
}
