package ru.yandex.practicum;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public static WordleDictionary load(Reader reader) throws IOException, WordleDictionaryIsEmptyException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            WordleDictionary dictionary = new WordleDictionary();

            String word;
            while ((word = bufferedReader.readLine()) != null) {
                dictionary.add(word);
            }
            if (dictionary.isEmpty()) {
                throw new WordleDictionaryIsEmptyException("");
            }
            return dictionary;
        }

    }
}
