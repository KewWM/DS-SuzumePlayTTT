
package SuzumePixelMap;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class MapSearch {

    private int[][] MapPiece1;
    private int[][] MapPiece2;
    private int[][] MapPiece3;
    private int[][] MapPiece4;
    private final int width = 10, height = 20;

    private final int[][] Directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int[][] ImageConvert(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        BufferedImage image = ImageIO.read(imageFile);

        int[][] pixelConverted = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = image.getRGB(x, y) & 0xFF;
                int convertedValue = pixelValue / 64;
                pixelConverted[y][x] = convertedValue;
            }
        }
        return pixelConverted;
    }

    public void ImageReader() {
        try {
            MapPiece1 = ImageConvert("./src/MapPiece/image 1.png");
            MapPiece2 = ImageConvert("./src/MapPiece/image 2.png");
            MapPiece3 = ImageConvert("./src/MapPiece/image 3.png");
            MapPiece4 = ImageConvert("./src/MapPiece/image 4.png");

        } catch (IOException e) {
            System.err.println("Error reading image file");
            e.printStackTrace();
        }
    }
    
    public void printPixelValues(int[][] array) {
        for (int[] row : array) {
            for (int value : row) {
                System.out.printf("%-3d", value * 64); // Print original pixel value before conversion
            }
            System.out.println();
        }
    }
    
    public void DispMapArray(int[][] array) {
        for (int[] row : array) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
    
    
    
    public int dfs(int[][] MP, int currX, int currY, int visitedStations, boolean[][] visited) {
        if (currX < 0 || currX >= MP[0].length || currY < 0 || currY >= MP.length
                || MP[currY][currX] == 1 || visitedStations > 4)
            return 0;

        if (visited[currY][currX])
            return 0;

        if (MP[currY][currX] == 2)
            visitedStations++;

        if (visitedStations == 4 && MP[currY][currX] == 3)
            return 1;

        visited[currY][currX] = true;
        int pathCount = 0;

        for (int[] direction : Directions) {
            int newX = currX + direction[0];
            int newY = currY + direction[1];
            pathCount += dfs(MP, newX, newY, visitedStations, visited);
        }

        visited[currY][currX] = false;

        if (MP[currY][currX] == 2)
            visitedStations--;

        return pathCount;
    }

    public int countPaths(int[][] MapPiece) {
        boolean[][] visited = new boolean[MapPiece.length][MapPiece[0].length];
        return dfs(MapPiece, 0, 0, 0, visited);
    }

    public int[][] FullMap() {
        int[][] TempMP1 = new int[20][10], TempMP2 = new int[20][10],
                TempMP3 = new int[20][10], TempMP4 = new int[20][10];

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10 ; j++){
                TempMP1[i][j] = MapPiece1[i][j];
            }
        }

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10 ; j++){
                TempMP2[i][j] = MapPiece2[i][j];
            }
        }

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10 ; j++){
                TempMP3[i][j] = MapPiece3[i][j];
            }
        }

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10 ; j++){
                TempMP4[i][j] = MapPiece4[i][j];
            }
        }

        TempMP1[19][9] = 1;
        TempMP2[19][9] = 1;
        TempMP3[19][9] = 1;

        int[][] FullMap = new int[40][20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                FullMap[i][j] = TempMP1[i][j];
            }
        }

        for (int i = 0, y = 0; i < 20; i++, y++) {
            for (int j = 10, x = 0; j < 20; j++, x++) {
                FullMap[i][j] = TempMP2[y][x];
            }
        }

        for (int i = 20, y = 0; i < 40; i++, y++) {
            for (int j = 0, x = 0; j < 10; j++, x++) {
                FullMap[i][j] = TempMP3[y][x];
            }
        }

        for (int i = 20, y = 0; i < 40; i++, y++) {
            for (int j = 10, x = 0; j < 20; j++, x++) {
                FullMap[i][j] = TempMP4[y][x];
            }
        }

        return FullMap;
    }

    public int[][] getMapPiece1() {
        return MapPiece1;
    }

    public int[][] getMapPiece2() {
        return MapPiece2;
    }

    public int[][] getMapPiece3() {
        return MapPiece3;
    }

    public int[][] getMapPiece4() {
        return MapPiece4;
    }

    public int bfs(int[][] MapPiece) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] startVisited = new boolean[MapPiece.length][MapPiece[0].length];
        startVisited[0][0] = true;
        queue.offer(new int[]{0, 0, 0, 0}); // starting node: x, y, visitedStations, visited[][] index

        List<boolean[][]> visitedList = new ArrayList<>();
        visitedList.add(startVisited);

        int pathCount = 0;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int currX = curr[0];
            int currY = curr[1];
            int visitedStations = curr[2];
            int visitedIndex = curr[3];
            boolean[][] currVisited = visitedList.get(visitedIndex);

            if (MapPiece[currY][currX] == 3 && visitedStations == 3) {
                pathCount++;
                continue;
            }

            for (int[] direction : Directions) {
                int newX = currX + direction[0];
                int newY = currY + direction[1];

                if (newX < 0 || newX >= MapPiece[0].length || newY < 0 || newY >= MapPiece.length
                        || MapPiece[newY][newX] == 1 || currVisited[newY][newX])
                    continue;

                int newVisitedStations = visitedStations;
                if (MapPiece[newY][newX] == 2 && visitedStations < 3) {
                    newVisitedStations++;
                } else if (MapPiece[newY][newX] == 2) {
                    continue; 
                }

                if (MapPiece[newY][newX] == 3 && newVisitedStations < 3)
                    continue;

                boolean[][] newVisited = new boolean[MapPiece.length][MapPiece[0].length];
                for (int i = 0; i < MapPiece.length; i++) {
                    System.arraycopy(currVisited[i], 0, newVisited[i], 0, MapPiece[0].length);
                }
                newVisited[newY][newX] = true;

                visitedList.add(newVisited);
                int newVisitedIndex = visitedList.size() - 1;
                queue.offer(new int[]{newX, newY, newVisitedStations, newVisitedIndex});
            }
        }
        return pathCount;
    }

}