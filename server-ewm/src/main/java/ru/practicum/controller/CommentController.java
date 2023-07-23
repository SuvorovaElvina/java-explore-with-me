package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentEventDto;
import ru.practicum.dto.RequestCommentDto;
import ru.practicum.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService service;

    @PostMapping("/users/{userId}/comment")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto createComment(@Positive @RequestParam Long eventId,
                                    @Positive @PathVariable Long userId,
                                    @Valid @RequestBody RequestCommentDto commentDto) {
        return service.create(eventId, userId, commentDto);
    }

    @PatchMapping("/users/{userId}/comment/{comId}")
    public CommentDto updateComment(@Positive @PathVariable Long userId,
                                    @Positive @PathVariable Long comId,
                                    @Valid @RequestBody RequestCommentDto commentDto) {
        return service.update(userId, comId, commentDto);
    }

    @GetMapping("/users/{userId}/comment")
    public List<CommentEventDto> getEventByCommentAndUser(@Positive @PathVariable Long userId,
                                                          @RequestParam(defaultValue = "0") Integer from,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        return service.getCommentEventDtoByUser(userId, from, size);
    }

    @GetMapping("/admin/comment")
    public List<CommentDto> getAllComment(@RequestParam(name = "start", required = false) String startStr,
                                          @RequestParam(name = "end", required = false) String endStr,
                                          @RequestParam(defaultValue = "NEW") String sort,
                                          @RequestParam(defaultValue = "0") Integer from,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return service.getAllCommentDto(startStr, endStr, sort, from, size);
    }

    @DeleteMapping("/users/{userId}/comment")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUser(@Positive @PathVariable Long userId, @RequestParam(required = false) List<Long> comsId) {
        service.deleteByUser(userId, comsId);
    }

    @DeleteMapping("/admin/comment")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByAdmin(@RequestParam List<Long> comsId) {
        service.deleteByAdmin(comsId);
    }
}
