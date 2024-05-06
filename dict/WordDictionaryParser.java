package dict;

import java.io.*;
import java.util.*;

public class WordDictionaryParser {
    public static Set<String> parseDictionary(String filePath) {
        Set<String> wordDictionary = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                wordDictionary.add(word.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordDictionary;
    }

    public static Map<String, Set<String>> buildWordLadder(Set<String> wordDictionary) {
        Map<String, Set<String>> wordLadder = new HashMap<>();
        
        for (String key : wordDictionary) {
            Set<String> connections = new HashSet<>();
            for (int i = 0; i < key.length(); i++) {
                for (char c = 'A'; c <= 'Z'; c++) {
                    StringBuilder newWord = new StringBuilder(key);

                    newWord.setCharAt(i, c);
                    if (wordDictionary.contains(newWord.toString()) && !newWord.toString().equals(key)) {
                        connections.add(newWord.toString());
                    }
                }
            }
            wordLadder.put(key, connections);
        }
    
        return wordLadder;
    }
}
