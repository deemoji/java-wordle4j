package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.PrintWriter;
import java.util.List;

public class WordleGameTest {

    private PrintWriter writer;
    private WordleDictionary dictionary;
    private String answer = "абвгд";
    private WordleGame logic;


    @BeforeEach
    public void beforeEach() {
        writer = new PrintWriter(System.out);
        dictionary = new WordleDictionary(List.of("ааааа", "ббббб", "ввввв", "абвгд"), writer);
        logic = new WordleGame(answer, 3, dictionary, writer);
    }


    @Test
    public void testCheckGameOverAndWinIsTrue() {
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
    public void testCheckGameOverAndWinIsFalse() {
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
    public void testCheckWordNotFoundExceptionWhenWordLengthIs6() {
        try {
            logic.makeStep("аптека");
        } catch (WordNotFoundException e) {
            assertEquals("Слово <аптека> отсутствует в словаре!", e.getMessage());
            return;
        }
        assertFalse(logic.gameOver());
    }

    @Test
    public void testCheckWordNotFoundExceptionWhenWordIsNotInDictionary() {
        try {
            logic.makeStep("абзац");
        } catch (WordNotFoundException e) {
            assertEquals("Слово <абзац> отсутствует в словаре!", e.getMessage());
            return;
        }
        assertFalse(logic.gameOver());
    }

    @Test
    public void testCheckNotGameOverWhenWordNotFoundException() {
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
