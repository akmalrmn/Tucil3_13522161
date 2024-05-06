import java.io.*;
import java.util.*;

import algorithms.ucs.Ucs;
import algorithms.gbfs.Gbfs;
import algorithms.astar.Astar;
import dict.WordDictionaryParser;

public class Main {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    String dictionaryFilePath = "./dict/dictionary.txt";

    Set<String> wordDictionary = WordDictionaryParser.parseDictionary(dictionaryFilePath);
    File graphFile = new File("./bin/data/word_ladder.ser");
    Map<String, Set<String>> wordLadder;

    if (graphFile.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(graphFile))) {
        wordLadder = (Map<String, Set<String>>) ois.readObject();
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        return;
      }
    } else {
      wordLadder = WordDictionaryParser.buildWordLadder(wordDictionary);

      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(graphFile))) {
        oos.writeObject(wordLadder);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    Scanner scanner = new Scanner(System.in);
    int choice;

    while (true) {
      System.out.println("1. UCS");
      System.out.println("2. Greedy BFS");
      System.out.println("3. A*");
      System.out.println("4. Exit");
      System.out.print("Choose the Algorithm: ");
      try {
        choice = scanner.nextInt();

        if (choice < 1 || choice > 4) {
          System.out.println("\nInvalid choice\n");
          continue;
        }

        if (choice == 4) {
          System.out.println("\nThank you for using the program.");
          System.out.println("#Semester4CepatKelarDong");
          System.out.println("#Udah6TubesNih");
          System.out.println("#TubesOSMasihKeyboard :(");
          scanner.close();
          System.exit(0);
        }

        System.out.print("\nEnter the initial word: ");
        String initial = scanner.next().toUpperCase();
        if (!initial.matches("[A-Za-z]+") || !wordDictionary.contains(initial)) {
          System.out.println("\nInvalid input. Please enter a word in the dictionary.\n");
          continue;
        }
        System.out.print("Enter the goal word: ");
        String goal = scanner.next().toUpperCase();
        if (!goal.matches("[A-Za-z]+") || !wordDictionary.contains(goal)) {
          System.out.println("\nInvalid input. Please enter a word in the dictionary.\n");
          continue;
        }
        if (initial.length() != goal.length()) {
          System.out.println("\nInvalid input. The initial and goal words must have the same length.\n");
          continue;
        }
        List<String> result;
        switch (choice) {
          case 1:
            Ucs ucs = new Ucs();
            result = ucs.algorithms(initial, goal, wordLadder);
            System.out.println("Path: " + result + "\n");
            System.out.println("Number of steps: " + (result.size() -1) + "\n");
            break;
          case 2:
            Gbfs gbfs = new Gbfs();
            result = gbfs.algorithms(initial, goal, wordLadder);
            System.out.println("Path: " + result + "\n");
            System.out.println("Number of steps: " + (result.size() -1) + "\n");
            break;
          case 3:
            Astar astar = new Astar();
            result = astar.algorithms(initial, goal, wordLadder);
            System.out.println("Path: " + result + "\n");
            System.out.println("Number of steps: " + (result.size() -1) + "\n");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("\nInvalid choice\n");
        scanner.nextLine();
        continue;
      } catch (NoSuchElementException e) {
        System.out.println("\nInvalid choice\n");
        scanner.nextLine();
        continue;
      }
    }
  }
}