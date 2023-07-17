package ru.practicum.service;

import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.*;
import ru.practicum.model.Event;

import java.util.List;

public interface EventService {
    EventDto create(NewEventDto newEventDto, Long userId);

    List<EventDto> getAll(List<Long> users, List<String> states, List<Long> catsId, String startStr, String endStr, int from, int size);

    EventDto published(Long id, UpdateEventDto eventDto);

    List<EventDto> getAllByUser(Long userId, int from, int size);

    List<EventDto> getAllPublic(String text, Boolean paid, List<Long> catsId, String startStr, String endStr,
                                boolean onlyAvailable, String sortStr, int from, int size);

    EventDto getPublicById(Long id);

    EventDto getForUserById(Long userId, Long eventId);

    EventDto update(Long userId, Long eventId, UpdateEventDto eventDto);

    Event getEventById(Long id);

    Event saveEvent(Event event);

    List<Event> getAllEvents(List<Long> ids);

    List<EventShortDto> getShortEvent(List<Event> events);
}
