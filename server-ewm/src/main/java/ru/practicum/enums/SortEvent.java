package ru.practicum.enums;

public enum SortEvent {
    EVENT_DATE, VIEWS;

    public static SortEvent fromString(String state) {
        return SortEvent.valueOf(state);
    }
}
