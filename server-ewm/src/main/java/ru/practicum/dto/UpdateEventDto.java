package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.enums.StateAction;
import ru.practicum.model.Location;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventDto {
    @Size(min = 20, max = 2000, message = "Краткое описание не может быть меньше 20 и превышать 2000.")
    String annotation;

    Long category;

    @Size(min = 20, max = 7000, message = "Описание не может быть меньше 20 и превышать 7000.")
    String description;

    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    StateAction stateAction;

    @Size(min = 3, max = 120, message = "Название не должно быть меньше 3 и превышать 120.")
    String title;
}
