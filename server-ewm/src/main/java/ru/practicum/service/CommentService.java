package ru.practicum.service;

import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentEventDto;
import ru.practicum.dto.RequestCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto create(Long eventId, Long userId, RequestCommentDto commentDto);

    CommentDto update(Long userId, Long comId, RequestCommentDto commentDto);

    List<CommentEventDto> getCommentEventDtoByUser(Long userId, int from, int size);

    List<CommentDto> getAllCommentDto(String startStr, String endStr, String sort, int from, int size);

    void deleteByUser(Long userId, List<Long> comsId);

    void deleteByAdmin(List<Long> comsId);
}
