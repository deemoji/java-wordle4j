package ru.yandex.practicum;

public class WordleDictionaryIsEmptyException extends Exception {
    public WordleDictionaryIsEmptyException(String message) {
        super(message);
    }
}
