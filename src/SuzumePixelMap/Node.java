
package SuzumePixelMap;

import java.util.HashSet;

public class Node {
    int row;
    int col;
    int dist;
    HashSet<String> visited;
    Node parent;
    int numStations;

    Node(int row, int col, int dist, HashSet<String> visited, Node parent, int numStations) {
        this.row = row;
        this.col = col;
        this.dist = dist;
        this.visited = visited;
        this.parent = parent;
        this.numStations = numStations;
    }
}


