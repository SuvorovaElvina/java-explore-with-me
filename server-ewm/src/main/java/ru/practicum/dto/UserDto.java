package ru.practicum.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;

    @Size(min = 2, max = 250, message = "Имя не может быть меньше 2 и больше 250")
    @NotBlank(message = "Имя не должно быть пустым или null.")
    String name;

    @Email
    @Size(min = 6, max = 254, message = "Email не может быть меньше 6 и больше 254")
    @NotBlank(message = "Email не должен быть пустым или null.")
    String email;
}
