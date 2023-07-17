package ru.practicum.service;

import ru.practicum.HitDto;
import ru.practicum.NewHitDto;
import ru.practicum.StatsDto;

import java.util.List;

public interface StatService {
    HitDto create(NewHitDto dto);

    List<StatsDto> getStatus(String start, String end, List<String> uris, Boolean unique);

    Long getViews(String uris);
}
