package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentEventDto;
import ru.practicum.dto.RequestCommentDto;
import ru.practicum.enums.SortComment;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.repository.CommentRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final UserService userService;
    private final EventService eventService;
    private final CommentMapper mapper;

    @Override
    @Transactional
    public CommentDto create(Long eventId, Long userId, RequestCommentDto commentDto) {
        Comment comment = mapper.toComment(commentDto, eventService.getEventById(eventId), userService.getUser(userId));
        return mapper.toCommentDto(repository.save(comment));
    }

    @Override
    @Transactional
    public CommentDto update(Long userId, Long comId, RequestCommentDto commentDto) {
        Comment comment = repository.findById(comId).orElseThrow(() ->
                new NotFoundException(String.format("Комментария с id %d не найдено", comId)));
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ValidationException("Вы не являетей создателем этого комментария");
        } else {
            comment.setText(commentDto.getText());
        }
        return mapper.toCommentDto(repository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentEventDto> getCommentEventDtoByUser(Long userId, int from, int size) {
        if (userId == 0) {
            throw new ValidationException("Пользователь не может быть с id равным 0");
        }
        int pageNumber = (int) Math.ceil((double) from / size);
        List<Long> eventsId = repository.findByAuthorId(userId)
                .stream()
                .map(comment -> comment.getEvent().getId())
                .collect(Collectors.toList());
        return eventService.getCommentEventDto(eventsId, pageNumber, size);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentDto(String startStr, String endStr, String sort, int from, int size) {
        int pageNumber = (int) Math.ceil((double) from / size);
        SortComment sorts = SortComment.fromString(sort);
        LocalDateTime start = null;
        LocalDateTime end = null;
        List<CommentDto> commentDtos = List.of();
        if (startStr != null) {
            start = dateFromString(startStr);
        }
        if (endStr != null) {
            end = dateFromString(endStr);
        }
        switch (sorts) {
            case NEW:
                if (start != null && end != null) {
                    if (start.isAfter(end) || start.isEqual(end)) {
                        throw new ValidationException("Start не должен быть позже end или быть равным ему.");
                    } else {
                        commentDtos = repository.findByCreatedBeforeAndCreatedAfter(end, start,
                                PageRequest.of(pageNumber, size, Sort.by("created").descending()))
                                .stream()
                                .map(mapper::toCommentDto)
                                .collect(Collectors.toList());
                    }
                } else if (start == null && end == null) {
                    commentDtos = repository.findAll(PageRequest.of(pageNumber, size,
                                    Sort.by("created").descending())).stream()
                            .map(mapper::toCommentDto)
                            .collect(Collectors.toList());
                } else {
                    throw new ValidationException("Нужна указывать либо оба параметра start, end или не указывать вообще.");
                }
                break;
            case OLD:
                if (start != null && end != null) {
                    if (start.isAfter(end) || start.isEqual(end)) {
                        throw new ValidationException("Start не должен быть позже end или быть равным ему.");
                    } else {
                        commentDtos = repository.findByCreatedBeforeAndCreatedAfter(end, start,
                                        PageRequest.of(pageNumber, size, Sort.by("created").ascending()))
                                .stream()
                                .map(mapper::toCommentDto)
                                .collect(Collectors.toList());
                    }
                } else if (start == null && end == null) {
                    commentDtos = repository.findAll(PageRequest.of(pageNumber, size,
                                    Sort.by("created").ascending())).stream()
                            .map(mapper::toCommentDto)
                            .collect(Collectors.toList());
                } else {
                    throw new ValidationException("Нужна указывать либо оба параметра start, end или не указывать вообще.");
                }
                break;
        }
        return commentDtos;
    }

    @Override
    @Transactional
    public void deleteByUser(Long userId, List<Long> comsId) {
        if (comsId == null) {
            if (userId != 0) {
                repository.deleteByAuthorId(userId);
            } else {
                throw new ValidationException("Id пользователя не должно быть 0");
            }
        } else {
            List<Long> validComId = comsId.stream().filter(comId -> comId <= 0).collect(Collectors.toList());
            if (validComId.size() > 0) {
                throw new ValidationException("Id комментариев не должно быть меньше или равным 0");
            }
            List<Comment> comments = repository.findAllById(comsId);
            List<Long> validComments = comments.stream()
                    .filter(comment -> !comment.getAuthor().getId().equals(userId))
                    .map(Comment::getId)
                    .collect(Collectors.toList());
            if (validComments.size() > 0) {
                throw new ValidationException(String.format("Вы не являетей создателем комментариев: %s", validComments));
            } else {
                repository.deleteAllById(comsId);
            }
        }
    }

    @Override
    @Transactional
    public void deleteByAdmin(List<Long> comsId) {
        if (comsId != null) {
            List<Long> validComId = comsId.stream().filter(comId -> comId <= 0).collect(Collectors.toList());
            if (validComId.size() > 0) {
                throw new ValidationException("Id комментариев не должно быть меньше или равным 0");
            }
            try {
                repository.deleteAllById(comsId);
            } catch (EmptyResultDataAccessException e) {
                throw new ConflictException("Возможно некоторые комментарии были удалены, проверьте перед повторным удалением.");
            }
        }
    }

    private LocalDateTime dateFromString(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateStr, formatter);
    }
}
