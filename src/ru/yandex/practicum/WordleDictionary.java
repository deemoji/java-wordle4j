package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words = new ArrayList<>();

    public WordleDictionary() {

    }

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public String[] getWords() {
        return words.toArray(words.toArray(new String[0]));
    }

    public String getRandomWord() throws WordleDictionaryIsEmptyException {
        if (words.isEmpty()) {
            throw new WordleDictionaryIsEmptyException("Словарь пуст");
        }
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }
    public void add(final String word) {
        if (word.length() != Constants.WORD_LENGTH) {
            return;
        }
        words.add(normalize(word));
    }

    public boolean contains(final String word) {
        return words.contains(normalize(word));
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public static String normalize(final String word) {
        return word.toLowerCase().replace('ё', 'е');
    }

    public static String compare(final String initialWord, final String comparableWord) {
        if (initialWord.length() != comparableWord.length()) return "";

        String[] result = new String[initialWord.length()];
        Map<Character, Integer> charsCountMap = new HashMap<>();
        for (int i = 0; i < initialWord.length(); i++) {
            char initialChar = initialWord.charAt(i);
            char comparableChar = comparableWord.charAt(i);

            if (initialChar == comparableChar) {
                result[i] = "+";
                continue;
            }

            charsCountMap.put(initialChar, charsCountMap.getOrDefault(initialChar, 0) + 1);
        }

        for (int i = 0; i < comparableWord.length(); i++) {
            if (result[i] != null) continue;

            char comparableChar = comparableWord.charAt(i);
            if (charsCountMap.containsKey(comparableChar) && charsCountMap.get(comparableChar) > 0) {
                charsCountMap.put(comparableChar, charsCountMap.get(comparableChar) - 1);
                result[i] = "^";
                continue;
            }
            result[i] = "-";
        }

        StringBuilder builder = new StringBuilder(result.length);

        for (String item : result) {
            builder.append(item);
        }

        return builder.toString();
    }
}
