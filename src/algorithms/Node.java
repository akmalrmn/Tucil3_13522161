package src.algorithms;

public class Node {
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

  int getHCost() {
    return hCost;
  }

  int getGCost() {
    return gCost;
  }
}
