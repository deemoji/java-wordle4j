package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordleDictionaryTest {

    private WordleDictionary dictionary;

    @BeforeEach
    public void beforeEach() {
        dictionary = new WordleDictionary();
    }

    @Test
    public void testShouldAddWordsToDictionary() {
        dictionary.add("ААААА");
        dictionary.add("Ёёёаё");
        dictionary.add("АбвгД");

        String[] expectedWords = new String[] {"ааааа", "еееае", "абвгд"};
        assertArrayEquals(expectedWords, dictionary.getWords());
    }

    @Test
    public void testShouldContainWordInDictionary() {
        dictionary.add("ААААА");
        dictionary.add("Ёёёаё");
        dictionary.add("АбвгД");

        assertTrue(dictionary.contains("абвгд"));
    }

    @Test
    public void testShouldNotContainWordInDictionary() {
        dictionary.add("ААААА");
        dictionary.add("Ёёёаё");
        dictionary.add("АбвгД");

        assertFalse(dictionary.contains("жжжжж"));
    }

    @Test
    public void testShouldNotAddWordWithExtraLength() {
        dictionary.add("ААААА");
        dictionary.add("ББББББ");

        assertArrayEquals(new String[] {"ааааа"}, dictionary.getWords());
    }

    @Test
    public void testShouldNormalizeSuccessfully() {
        assertEquals("еееае", WordleDictionary.normalize("Ёёёаё"));
    }

    @Test
    public void testShouldCompareAaaaaAndAaaaaSuccessfully() {
        assertEquals("+++++", WordleDictionary.compare("ааааа", "ааааа"));
    }

    @Test
    public void testShouldCompareAaaaaAndBbbbbSuccessfully() {
        assertEquals("-----", WordleDictionary.compare("ааааа", "ббббб"));
    }

    @Test
    public void testShouldCompareАbvgdAndDgbvaSuccessfully() {
        assertEquals("^^^^^", WordleDictionary.compare("абвгд", "дгбва"));
    }

    @Test
    public void testShouldCompareAaaabAndAabbaSuccessfully() {
        assertEquals("++^-^", WordleDictionary.compare("ааааб", "аабба"));
    }
}
