package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.*;
import ru.practicum.model.Event;
import ru.practicum.model.Location;

@Component
public class EventMapper {
    public Event toEvent(NewEventDto eventDto) {
        return Event.builder()
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .lat(eventDto.getLocation().getLat())
                .lon(eventDto.getLocation().getLon())
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(eventDto.getRequestModeration())
                .title(eventDto.getTitle())
                .build();
    }

    public EventDto toEventDto(Event event, UserShortDto userShortDto, CategoryDto categoryDto) {
        return EventDto.builder()
                .annotation(event.getAnnotation())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())
                .id(event.getId())
                .state(event.getState())
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn())
                .location(Location.builder().lat(event.getLat()).lon(event.getLon()).build())
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(userShortDto)
                .category(categoryDto)
                .build();
    }

    public EventShortDto toEventShortDto(Event event, UserShortDto userShortDto, CategoryDto categoryDto) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .eventDate(event.getEventDate())
                .paid(event.getPaid())
                .title(event.getTitle())
                .category(categoryDto)
                .initiator(userShortDto)
                .id(event.getId())
                .build();
    }
}
