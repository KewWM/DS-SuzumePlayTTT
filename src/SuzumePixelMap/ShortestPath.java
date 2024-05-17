
package SuzumePixelMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class ShortestPath {
    private static final int[][] movement = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final String[] direction = {"Up", "Down", "Left", "Right"};

    public static List<List<String>> FindShortestPaths(int[][] map) {
        List<List<String>> shortestPaths = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        HashSet<String> firstPosition = new HashSet<>();
        firstPosition.add("0,0");
        queue.offer(new Node(0, 0, 0, firstPosition, null, 0));

        int shortestDist = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            Node curr = queue.poll();

            if (curr.dist > shortestDist) {
                continue;
            }

            if (map[curr.row][curr.col] == 3 && curr.numStations == 4) {
                if (curr.dist < shortestDist) {
                    shortestPaths.clear();
                    shortestDist = curr.dist;
                }

                List<String> path = new ArrayList<>();
                Node node = curr;
                while (node.parent != null) {

                    int rowDirection = node.row - node.parent.row;
                    int colDirection = node.col - node.parent.col;
                    for (int i = 0; i < movement.length; i++) {
                        if (movement[i][0] == rowDirection && movement[i][1] == colDirection) {
                            path.add(direction[i]);
                            break;
                        }
                    }
                    node = node.parent;
                }
                Collections.reverse(path);
                shortestPaths.add(path);
                continue;
            }

            for (int i = 0; i < movement.length; i++) {
                int newRow = curr.row + movement[i][0];
                int newCol = curr.col + movement[i][1];
                String key = newRow + "," + newCol;

                int newNumStations = curr.numStations;
                if (map[curr.row][curr.col] != 0) {
                    newNumStations++;
                }

                if (isValid(map, newRow, newCol) && !curr.visited.contains(key)) {
                    HashSet<String> newVisited = new HashSet<>(curr.visited);
                    newVisited.add(key);
                    queue.offer(new Node(newRow, newCol, curr.dist + 1, newVisited, curr, newNumStations));
                }
            }
        }
        return shortestPaths;
    }

    private static boolean isValid(int[][] map, int height, int width) {
        if (height < 0 || height >= map.length || width < 0 || width >= map[0].length) {
            return false;
        }
        int value = map[height][width];
        return value != 1;
    }
}
