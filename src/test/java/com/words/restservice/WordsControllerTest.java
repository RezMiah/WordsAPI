package com.words.restservice;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordsControllerTest {

    @Test
    void whenGetWordCountIsCalledCorrectNumberOfWordsReturned() {
        int wordCount  = WordsController.getWordCount("Hello World 345 & .!");

        assertEquals(5, wordCount);
    }

    @Test
    void whenUpdateWordsLengthIsCalledThenLengthMapContainsCorrectWordLengthsCount(){
        HashMap<Integer,Integer> lengthMap = new HashMap<>();

        lengthMap = new WordsController().updateWordsLength("ghrt nhjr2 4rtt", lengthMap);

        assertEquals(2,lengthMap.get(4));
        assertEquals(1,lengthMap.get(5));
    }

    @Test
    void whenGetAverageWordLengthIsCalledThenCorrectAverageLengthIsReturned(){
        HashMap<Integer,Integer> lengthMap = new HashMap<>();
        lengthMap.put(1, 3);
        lengthMap.put(2, 4);
        lengthMap.put(3, 5);

        double averageLength = new WordsController().getAverageWordLength(lengthMap);

        assertEquals(7.666666666666666, averageLength);
    }

    @Test
    void whenFrequentWordsIsCalledThenListOfFrequentLengthsIsReturned(){
        HashMap<Integer,Integer> lengthMap = new HashMap<>();
        lengthMap.put(1, 1);
        lengthMap.put(2, 1);
        lengthMap.put(3, 1);
        lengthMap.put(4, 2);
        lengthMap.put(5, 2);
        lengthMap.put(6, 1);

        List<Integer> frequentWords = new WordsController().frequentWords(lengthMap);

        assertEquals(4,frequentWords.get(0));
        assertEquals(5, frequentWords.get(1));
        assertEquals(2, frequentWords.size());
    }
}
