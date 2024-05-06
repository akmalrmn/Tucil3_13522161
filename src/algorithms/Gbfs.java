package src.algorithms;

import java.util.*;

public class Gbfs {
  public List<String> algorithms(String initial, String goal, Map<String, Set<String>> wordLadder) {
    long startTime = System.currentTimeMillis();
    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

    Set<String> visited = new HashSet<>();
    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getHCost));
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
            nextNode.parent = current;  // Set the parent of the node
            nextNode.hCost = countHeuristic(connection, goal);
            nodes.put(connection, nextNode);
            queue.add(nextNode);
          }
        }
      }
    }

    LinkedList<String> path = new LinkedList<>();
    for (Node node = nodes.get(goal); node != null; node = node.parent) {
      path.addFirst(node.word);
    }

    printPerformanceMetrics(startTime, runtime, usedMemoryBefore, visited.size());
    return path;
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

  private void printPerformanceMetrics(long startTime, Runtime runtime, long usedMemoryBefore, int visitedSize) {
    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("\nExecution time: " + elapsedTime + " milliseconds");
    System.out.println("Memory used: " + (usedMemoryAfter - usedMemoryBefore) + " bytes");
    System.out.println("Number of visited nodes: " + visitedSize);
  }
}