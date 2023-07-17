package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.RequestDto;
import ru.practicum.model.Request;

@Component
public class RequestMapper {
    public RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .requester(request.getRequester().getId())
                .created(request.getCreated())
                .id(request.getId())
                .event(request.getEvent().getId())
                .status(request.getStatus())
                .build();
    }
}
