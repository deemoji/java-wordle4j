package ru.yandex.practicum;

import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */

public class Wordle {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (PrintWriter logWriter = new PrintWriter("log.txt")) {
            try {
                FileReader reader = new FileReader("words_ru.txt", StandardCharsets.UTF_8);
                WordleDictionary dictionary = WordleDictionaryLoader.load(reader, logWriter);

                WordleGame game = new WordleGame(dictionary.getRandomWord(), Constants.STEPS, dictionary, logWriter);
                String word = "";
                System.out.println("Добро пожаловать в Wordle! Для начала игры введите слово:");
                while (!game.gameOver()) {
                    word = scanner.nextLine();
                    if (word.isEmpty()) {
                        word = game.getTip();
                        System.out.println(word);
                    }
                    try {
                        String stepResult = game.makeStep(word);
                        System.out.println(stepResult);
                    } catch (WordNotFoundException e) {
                        System.out.println("Такого слова нет в словаре! Введите новое:");
                    }
                }

                if (game.isWin()) {
                    System.out.println("Вы угадали слово. Поздравляем!");
                    return;
                }
                // else
                System.out.println("Попытки закончились. Загаданное слово - " + game.getAnswer() + ". Вы проиграли!");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Не удалось инициализировать PrintWriter!");
        }
    }
}
