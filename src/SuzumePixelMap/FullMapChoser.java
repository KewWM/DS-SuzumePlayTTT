
package SuzumePixelMap;

import java.util.List;
import java.util.Random;

public class FullMapChoser {
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String ANSI_YELLOW = "\u001B[33m"; // Coloured Text

    public static void main(String[] args) {
        MapSearch comMap = new MapSearch();
        comMap.ImageReader();

        int[][] copyMap = comMap.FullMap();

        List<List<String>> allShortestPaths = ShortestPath.FindShortestPaths(comMap.FullMap());

        Random r = new Random();
        List<String> path = allShortestPaths.get(r.nextInt(allShortestPaths.size())); // Randomly choose a path

        System.out.println("The chosen shortest path is: \n" + path);
        
        int[][] matrix = new int[40][20]; // Initialize the matrix with all zeros
        
        int currentRow = 0;
        int currentCol = 0;
        
        // Traverse the path and update the matrix accordingly
        for (String direction : path) {
            matrix[currentRow][currentCol] = 1; // Mark the current position as part of the path
            
            // Move in the specified direction
            if (direction.equals("Right")) {
                currentCol++;
            } else if (direction.equals("Left")) {
                currentCol--;
            } else if (direction.equals("Up")) {
                currentRow--;
            } else if (direction.equals("Down")) {
                currentRow++;
            }
        }
        
        // Print the matrix
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                if (matrix[i][j] == 1){
                    System.out.print(ANSI_YELLOW + matrix[i][j] + " " + RESET);
                }
                else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    
}
