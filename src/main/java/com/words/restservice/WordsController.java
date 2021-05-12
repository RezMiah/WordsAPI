package com.words.restservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@RestController
public class WordsController {
    public int freqWordLength;

    @GetMapping(value = "/WordDetails", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getWordsDetails() {
        return readFile("src\\main\\java\\com\\words\\restservice\\sampleWords.txt");
    }
    public String readFile(String filename){
        HashMap<Integer,Integer> lengthMap = new HashMap<>();
        int numberOfWords = 0;
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                numberOfWords += getWordCount(data);
                lengthMap = updateWordsLength(data, lengthMap);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return buildResponseString(numberOfWords,frequentWords(lengthMap), lengthMap);
    }

    public String buildResponseString(int numberOfWords,List<Integer> freqWordLengthsList, HashMap<Integer,Integer> lengthMap){
        StringBuilder returnData = new StringBuilder();
        returnData.append("\n Word count = ").append(numberOfWords);
        for(int i : lengthMap.keySet()){
            returnData.append("\n Number of words of length ").append(i).append(" is ").append(lengthMap.get(i));
        }
        returnData.append("\n Average word length = ").append(getAverageWordLength(lengthMap));
        returnData.append("\n The most frequently occurring word length is ").append(freqWordLength).append(" for word lengths of: \n");
        for (int i : freqWordLengthsList) {
            returnData.append(i).append("\n");
        }
        return returnData.toString();
    }

    public static int getWordCount(String line){
        return line.split(" ").length;
    }

    public HashMap<Integer,Integer> updateWordsLength(String line, HashMap<Integer,Integer> lengthMap){
        String[] words = line.split(" ");
        for(int i = 0; i < words.length; i++){
            if (!lengthMap.containsKey(words[i].length())){
                lengthMap.put(words[i].length(),1);
            }else {
                lengthMap.put(words[i].length(), lengthMap.get(words[i].length())+1);
            }
        }
        return lengthMap;
    }

    public double getAverageWordLength(HashMap<Integer,Integer> lengthMap){
        double total = 0;
        for (int i: lengthMap.keySet()) {
            total = total + i * lengthMap.get(i);
        }
        return total/lengthMap.size()-1;
    }

    public List<Integer> frequentWords(HashMap<Integer,Integer> lengthMap){
        List<Integer> freqWords = new ArrayList<>();
        freqWordLength = 0;
        for (int i: lengthMap.keySet()) {
            if(lengthMap.get(i) > freqWordLength) {
                freqWordLength = lengthMap.get(i);
                freqWords.clear();
                freqWords.add(i);
            }
            else if (lengthMap.get(i) == freqWordLength){
                freqWords.add(i);
            }
        }
        return freqWords;
    }
}
