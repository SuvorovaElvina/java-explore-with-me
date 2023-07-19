package ru.practicum.service;

import ru.practicum.dto.UserDto;
import ru.practicum.model.User;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    List<UserDto> getAllDtoById(List<Long> ids, int from, int size);

    void delete(Long id);

    User getUser(Long id);

    List<User> getAllById(List<Long> ids);


}
