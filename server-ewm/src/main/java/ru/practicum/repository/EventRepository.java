package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.enums.State;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends EventCustomRepository, JpaRepository<Event, Long> {
    Page<Event> findByInitiatorInAndStateInAndCategoryInAndEventDateAfterAndEventDateBefore(List<User> users,
       List<State> states, List<Category> catsId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Event> findByInitiatorId(Long userId, Pageable pageable);

    Optional<Event> findByIdAndStateIn(Long id, List<State> state);

    @Query("select e from Event e " +
            "where (upper(e.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(e.description) like upper(concat('%', ?1, '%'))) " +
            "and e.category in (?2) and e.eventDate > ?3 and e.eventDate < ?4 and e.state = 'PUBLISHED'")
    Page<Event> searchPublicByAll(String text, List<Category> catsId, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
