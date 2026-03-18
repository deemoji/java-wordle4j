package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringReader;

public class WordleDictionaryLoaderTest {

    @Test
    public void testShouldLoadDictionarySuccessfully() {
        StringReader reader = new StringReader("ааааа\nБб\nЕёеёе\nабвгдЕ\nЖжЖжЖ\n");

        try (PrintWriter writer = new PrintWriter(System.out)) {
            WordleDictionary dictionary = WordleDictionaryLoader.load(reader, writer);

            assertArrayEquals(new String[] {"ааааа", "еееее", "жжжжж"}, dictionary.getWords());
        } catch (Exception e) {
            assertTrue(false);
        }

    }
}
