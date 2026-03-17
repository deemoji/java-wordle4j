package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.*;

public class WordleDictionaryTest {

    private PrintWriter writer;
    private WordleDictionary dictionary;

    private Set<Character> wrongChars;
    private Set<Character> correctChars;
    private Character[] correctPositions;
    Set<Character>[] wrongPositions;

    @BeforeEach
    public void beforeEach() {
        writer = new PrintWriter(System.out);
        dictionary = new WordleDictionary(writer);
        wrongChars = new HashSet<>();
        correctChars = new HashSet<>();
        correctPositions = new Character[Constants.WORD_LENGTH];
        wrongPositions = new HashSet[Constants.WORD_LENGTH];

        for (int i = 0; i < wrongPositions.length; i++) {
            wrongPositions[i] = new HashSet<>();
        }
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

        assertArrayEquals(new String[]{"ааааа"}, dictionary.getWords());
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

    @Test
    public void testShouldReturnAllWordsWhenEmptyFilters() {
        String[] words = new String[]{"болид", "мотор", "камаз"};
        for (String word : words) {
            dictionary.add(word);
        }

        assertEquals(Arrays.asList(words), dictionary.getFilteredWords(
                wrongChars,
                correctChars,
                correctPositions,
                wrongPositions
        ));
    }

    @Test
    public void testShouldFilterWordsWhenWrongChars() {
        wrongChars.add('б');
        wrongChars.add('р');

        String[] words = new String[] {"болид", "мотор", "камаз"};
        for (String word : words) {
            dictionary.add(word);
        }

        assertEquals(List.of("камаз"), dictionary.getFilteredWords(
                wrongChars,
                correctChars,
                correctPositions,
                wrongPositions
        ));
    }

    @Test
    public void testShouldFilterWordsWhenCorrectChars() {
        correctChars.add('р');
        correctChars.add('о');

        String[] words = new String[]{"болид", "мотор", "топор"};
        for (String word : words) {
            dictionary.add(word);
        }

        assertEquals(List.of("мотор", "топор"), dictionary.getFilteredWords(
                wrongChars,
                correctChars,
                correctPositions,
                wrongPositions
        ));
    }

    @Test
    public void testShouldFilterWordsWhenRightPositions() {
        correctPositions[1] = 'о';
        String[] words = new String[]{"болид", "мотор", "камаз"};
        for (String word : words) {
            dictionary.add(word);
        }

        assertEquals(List.of("болид", "мотор"), dictionary.getFilteredWords(
                wrongChars,
                correctChars,
                correctPositions,
                wrongPositions
        ));
    }

    @Test
    public void testShouldFilterWordsWhenWrongPositions() {
        wrongPositions[1].add('о');
        String[] words = new String[]{"болид", "мотор", "камаз"};
        for (String word : words) {
            dictionary.add(word);
        }

        assertEquals(List.of("камаз"), dictionary.getFilteredWords(
                wrongChars,
                correctChars,
                correctPositions,
                wrongPositions
        ));
    }

    @Test
    void testsShouldFilterWordsWhenAllFilters() {
        wrongChars.add('м');
        wrongChars.add('р');

        correctChars.add('о');
        correctChars.add('н');

        correctPositions[0] = 'о';

        wrongPositions[4].add('н');

        String[] words = new String[]{"огонь", "кирка", "болид", "мотор", "камаз", "окунь"};
        for (String word : words) {
            dictionary.add(word);
        }

        assertEquals(List.of("огонь", "окунь"), dictionary.getFilteredWords(
                wrongChars,
                correctChars,
                correctPositions,
                wrongPositions
        ));
    }
}
