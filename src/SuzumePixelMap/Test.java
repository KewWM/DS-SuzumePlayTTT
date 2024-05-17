package SuzumePixelMap;

import java.util.List;

public class Test {
     public static void main(String[] args) {
        MapSearch map = new MapSearch();
        map.ImageReader();
        int pathsMapPiece1 = map.bfs(map.getMapPiece1());
        int pathsMapPiece2 = map.bfs(map.getMapPiece2());
        int pathsMapPiece3 = map.bfs(map.getMapPiece3());
        int pathsMapPiece4 = map.bfs(map.getMapPiece4());
        
        System.out.println("Map 1:");
         System.out.println("Original Pixel values:");
        map.printPixelValues(map.getMapPiece1());
         System.out.println("Converted Pixel values:");
        map.DispMapArray(map.getMapPiece1());
        System.out.println("Number of possible paths (pass through 3 stations):" + pathsMapPiece1);
        
        System.out.println("\nMap 2:");
        System.out.println("Original Pixel values:");
        map.printPixelValues(map.getMapPiece2());
         System.out.println("Converted Pixel values:");
        map.DispMapArray(map.getMapPiece2());
        System.out.println("Number of possible paths (pass through 3 stations): " + pathsMapPiece2);
        
        System.out.println("\nMap 3:");
        System.out.println("Original Pixel values:");
        map.printPixelValues(map.getMapPiece3());
         System.out.println("Converted Pixel values:");
        map.DispMapArray(map.getMapPiece3());
        System.out.println("Number of possible paths (pass through 3 stations): " + pathsMapPiece3);
         
        System.out.println("\nMap 4:");
        System.out.println("Original Pixel values:");
        map.printPixelValues(map.getMapPiece4());
         System.out.println("Converted Pixel values:");
        map.DispMapArray(map.getMapPiece4());
        System.out.println("Number of possible paths (pass through 3 stations): " + pathsMapPiece4);
        
         System.out.println("\nCombined Map:");
        map.DispMapArray(map.FullMap());

        int nComp = map.countPaths(map.FullMap());
        System.out.println("Number of possible paths (pass through 4 stations) : " + nComp);


        MapSearch comMap = new MapSearch();
        comMap.ImageReader();

        List<List<String>> ShortestPathsCollection = ShortestPath.FindShortestPaths(comMap.FullMap());

        System.out.println("Minimum steps required : " + ShortestPathsCollection.get(0).size());
        System.out.println("Total possible shortest paths : " + ShortestPathsCollection.size());
        System.out.println("All possible shortest paths direction:");
        for (List<String> path : ShortestPathsCollection) {
            System.out.println(path);
        }
        System.out.println(ShortestPathsCollection.size());
        
       

    }
}