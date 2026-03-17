package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private final String answer;

    private int steps;

    private final WordleDictionary dictionary;

    private boolean isWin = false;

    private final Set<Character> wrongChars = new HashSet<>();
    private final Set<Character> correctChars = new HashSet<>();
    private final Character[] correctPositions = new Character[Constants.WORD_LENGTH];
    private final Set<Character>[] wrongPositions = new HashSet[Constants.WORD_LENGTH];

    private final PrintWriter logWriter;

    public WordleGame(String answer, int steps, WordleDictionary dictionary, PrintWriter logWriter) {
        this.answer = answer;
        this.steps = steps;
        this.dictionary = dictionary;
        this.logWriter = logWriter;
        for (int i = 0; i < Constants.WORD_LENGTH; i++) {
            wrongPositions[i] = new HashSet<>();
        }
    }

    public String getAnswer() {
        return answer;
    }
    public boolean gameOver() {
        return steps == 0 || isWin;
    }

    public boolean isWin() {
        return isWin;
    }

    public String getTip() {
        List<String> filteredWords = dictionary.getFilteredWords(
                wrongChars,
                correctChars,
                correctPositions,
                wrongPositions
        );
        Random random = new Random();
        if (filteredWords.isEmpty()) {
            String logMessage = "ОШИБКА: Отфильтрованный словарь содержит 0 элементов!" +
                    "wrongChars: " + wrongChars + "\n" +
                    "correctChars: " + correctChars + "\n" +
                    "correctPositions: " + Arrays.toString(correctPositions) + "\n" +
                    "wrongPositions: " + Arrays.toString(wrongPositions) + "\n";
            logWriter.println(logMessage);
        }
        return filteredWords.get(random.nextInt(filteredWords.size()));
    }

    public String makeStep(String guessWord) throws WordNotFoundException {
        if (gameOver()) {
            String logMessage = "ОШИБКА: Попытка сделать лишний ход!\n" +
                    "steps: " +
                    steps + "\n" +
                    "isWin: " +
                    isWin;

            logWriter.println(logMessage);
            throw new ExtraStepException("Попытка сделать лишний ход.\n");
        }

        if (answer.length() != guessWord.length()) {
            throw new WordNotFoundException("Слово <" + guessWord + "> отсутствует в словаре!");
        }

        if (guessWord.equals(answer)) {
            steps--;
            isWin = true;
            return "+".repeat(Constants.WORD_LENGTH);
        }
        if (dictionary.contains(guessWord)) {
            steps--;
            String compareResult = WordleDictionary.compare(answer, guessWord);
            splitCompareResult(compareResult, guessWord);
            return compareResult;
        }

        throw new WordNotFoundException("Слово <" + guessWord + "> отсутствует в словаре!");
    }

    private void splitCompareResult(String compareResult, String guessWord) {
        for (int i = 0; i < compareResult.length(); i++) {
            char resultChar = compareResult.charAt(i);
            char guessChar = guessWord.charAt(i);
            if (resultChar == '+') {
                correctPositions[i] = guessChar;
                correctChars.add(guessChar);
                continue;
            }
            if (resultChar == '^') {
                correctChars.add(guessChar);
                wrongPositions[i].add(guessChar);
            }

        }

        for (int i = 0; i < compareResult.length(); i++) {
            char resultChar = compareResult.charAt(i);
            char guessChar = guessWord.charAt(i);
            if (resultChar == '-' && !correctChars.contains(guessChar)) {
                wrongChars.add(guessChar);
            }
        }

    }
}
