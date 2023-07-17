package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.Location;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    @Size(min = 3, max = 120, message = "Название не должно быть меньше 3 и превышать 120.")
    @NotBlank
    String title;

    @NotBlank
    @Size(min = 20, max = 2000, message = "Краткое описание не может быть меньше 20 и превышать 2000.")
    String annotation;

    Long category;

    @Builder.Default
    Boolean paid = false;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @NotBlank
    @Size(min = 20, max = 7000, message = "Описание не может быть меньше 20 и превышать 7000.")
    String description;

    @Builder.Default
    Integer participantLimit = 0;

    @NotNull
    Location location;

    @Builder.Default
    Boolean requestModeration = true;
}
