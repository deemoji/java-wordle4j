package ru.yandex.practicum;

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

    public WordleGame(String answer, int steps, WordleDictionary dictionary) {
        this.answer = answer;
        this.steps = steps;
        this.dictionary = dictionary;
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

    public String makeStep(String guessWord) throws WordNotFoundException {
        if (gameOver()) {
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
            return WordleDictionary.compare(answer, guessWord);
        }

        throw new WordNotFoundException("Слово <" + guessWord + "> отсутствует в словаре!");
    }

}
