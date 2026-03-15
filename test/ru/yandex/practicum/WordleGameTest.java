package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class WordleGameTest {

    private WordleGame logic;
    private WordleDictionary dictionary = new WordleDictionary(List.of("ааааа", "ббббб", "ввввв", "абвгд"));
    private String answer = "абвгд";

    @BeforeEach
    public void beforeEach() {
        logic = new WordleGame(answer, 3, dictionary);
    }

    @Test
    void testCheckGameOverAndWinIsTrue() {
        try {
            String falseResult = logic.makeStep("ввввв");
            assertEquals("--+--", falseResult);
            String rightResult = logic.makeStep(answer);
            assertEquals("+++++", rightResult);
            assertTrue(logic.gameOver());
            assertTrue(logic.isWin());
        } catch (WordNotFoundException e) {
            assertEquals(true, false, "Код не должен взывать исключений");
        }
    }

    @Test
    void testCheckGameOverAndWinIsFalse() {
        try {
            String falseResult1 = logic.makeStep("ааааа");
            assertEquals("+----", falseResult1);
            String falseResult2 = logic.makeStep("ббббб");
            assertEquals("-+---", falseResult2);
            String falseResult3 = logic.makeStep("ввввв");
            assertEquals("--+--", falseResult3);
            assertTrue(logic.gameOver());
            assertFalse(logic.isWin());
        } catch (WordNotFoundException e) {
            assertEquals(true, false, "Код не должен взывать исключений");
        }
    }

    @Test
    void testCheckWordNotFoundExceptionWhenWordLengthIs6() {
        try {
            logic.makeStep("аптека");
        } catch (WordNotFoundException e) {
            assertEquals("Слово <аптека> отсутствует в словаре!", e.getMessage());
            return;
        }
        assertFalse(logic.gameOver());
    }

    @Test
    void testCheckWordNotFoundExceptionWhenWordIsNotInDictionary() {
        try {
            logic.makeStep("абзац");
        } catch (WordNotFoundException e) {
            assertEquals("Слово <абзац> отсутствует в словаре!", e.getMessage());
            return;
        }
        assertFalse(logic.gameOver());
    }

    @Test
    void testCheckNotGameOverWhenWordNotFoundException() {
        try {
            logic.makeStep("ааааа");
            logic.makeStep("ббббб");
            logic.makeStep("абзац");
        } catch (WordNotFoundException e) {
            assertEquals("Слово <абзац> отсутствует в словаре!", e.getMessage());
            return;
        }
        assertFalse(logic.gameOver());
    }

}
