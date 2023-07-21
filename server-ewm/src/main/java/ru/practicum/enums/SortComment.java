package ru.practicum.enums;

public enum SortComment {
    NEW, OLD;

    public static SortComment fromString(String state) {
        return SortComment.valueOf(state);
    }
}
