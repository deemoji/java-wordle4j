package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;
    private final PrintWriter logWriter;

    public WordleDictionary(PrintWriter logWriter) {
        this.words = new ArrayList<>();
        this.logWriter = logWriter;
    }

    public WordleDictionary(List<String> words, PrintWriter logWriter) {
        this.words = words;
        this.logWriter = logWriter;
    }

    public String[] getWords() {
        return words.toArray(words.toArray(new String[0]));
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public String getRandomWord() throws WordleDictionaryIsEmptyException {
        if (words.isEmpty()) {
            logWriter.println("ОШИБКА: Попытка получить случайный элемент в пустом словаре!");
            throw new WordleDictionaryIsEmptyException("Словарь пуст");
        }
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getFilteredWords(Set<Character> bannedChars,
                                         Set<Character> correctChars,
                                         Character[] correctPositions,
                                         Set<Character>[] wrongPositions) {
        List<String> filteredWords = new ArrayList<>();

        for (String word : words) {
            boolean wordToFilter = false;
            for (int i = 0; i < word.length(); i++) {
                if (bannedChars.contains(word.charAt(i))) {
                    wordToFilter = true;
                    break;
                }
            }
            if (wordToFilter) {
                continue;
            }

            for (char ch : correctChars) {
                if (word.indexOf(ch) == -1) {
                    wordToFilter = true;
                    break;
                }
            }

            for (int i = 0; i < correctPositions.length; i++) {
                if (correctPositions[i] != null && word.charAt(i) != correctPositions[i]) {
                    wordToFilter = true;
                    break;
                }

                if (wrongPositions[i].contains(word.charAt(i))) {
                    wordToFilter = true;
                    break;
                }
            }

            if (!wordToFilter) {
                filteredWords.add(word);
            }
        }

        return filteredWords;
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
