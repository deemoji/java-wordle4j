package ru.yandex.practicum;

public class WordNotFoundException extends WordleGameException {
    public WordNotFoundException(String message) {
        super(message);
    }
}
