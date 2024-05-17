
package SuzumePixelMap;

import java.util.List;

public class DirectionToMatrix {

   public static void convertToMatrix(List<String> path) {
        int[][] matrix = new int[40][20];

        int currentX = 0;
        int currentY = 0;

        // Mark the starting position as 0 in the matrix
        matrix[currentX][currentY] = 0;

        // Mark the path as 0 in the matrix
        for (String direction : path) {
            switch (direction) { 
                case "Up":
                    currentX--;
                    break;
                case "Down":
                    currentX++;
                    break;
                case "Left":
                    currentY--;
                    break;
                case "Right":
                    currentY++;
                    break;
            }

            // Check if the current position is within the matrix bounds
            if (currentX >= 0 && currentX < matrix.length && currentY >= 0 && currentY < matrix[currentX].length) {
                matrix[currentX][currentY] = 0;
            }
        }

        displayMatrix(matrix);
    }

    public static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}