package algorithms.astar;

import java.util.*;

public class Astar {
  public List<String> algorithms(String initial, String goal, Map<String, Set<String>> wordLadder) {
    long startTime = System.currentTimeMillis();
    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

    Set<String> visited = new HashSet<>();
    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getTotalCost));
    Map<String, Node> nodes = new HashMap<>();
    Node startNode = new Node(initial, 0, countHeuristic(initial, goal));
    nodes.put(initial, startNode);
    queue.add(startNode);

    while (!queue.isEmpty()) {
      Node current = queue.poll();
      visited.add(current.word);
      if (current.word.equals(goal)) {
        break;
      }
      Set<String> connections = wordLadder.get(current.word);
      if (connections != null) {
        for (String connection : connections) {
          if (!visited.contains(connection)) {
            Node nextNode = nodes.getOrDefault(connection, new Node(connection));
            nodes.put(connection, nextNode);
            int newCost = current.gCost + 1;
            if (newCost < nextNode.gCost) {
              nextNode.gCost = newCost;
              nextNode.hCost = countHeuristic(connection, goal);
              nextNode.parent = current;
              queue.add(nextNode);
            }
          }
        }
      }
    }

    LinkedList<String> path = new LinkedList<>();
    for (Node node = nodes.get(goal); node != null; node = node.parent) {
      path.addFirst(node.word);
    }

    printPerformanceMetrics(startTime, runtime, usedMemoryBefore);
    return path.isEmpty() || !path.getFirst().equals(initial) ? Collections.emptyList() : path;
  }

  private int countHeuristic(String current, String goal) {
    int count = 0;
    for (int i = 0; i < current.length(); i++) {
      if (current.charAt(i) != goal.charAt(i)) {
        count++;
      }
    }
    return count;
  }

  private void printPerformanceMetrics(long startTime, Runtime runtime, long usedMemoryBefore) {
    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("\nExecution time: " + elapsedTime + " milliseconds");
    System.out.println("Memory used: " + (usedMemoryAfter - usedMemoryBefore) + " bytes");
  }

  private static class Node {
    String word;
    int gCost;
    int hCost;
    Node parent;

    Node(String word) {
      this.word = word;
      this.gCost = Integer.MAX_VALUE;
      this.hCost = 0;
      this.parent = null;
    }

    Node(String word, int gCost, int hCost) {
      this.word = word;
      this.gCost = gCost;
      this.hCost = hCost;
      this.parent = null;
    }

    int getTotalCost() {
      return gCost + hCost;
    }
  }
}