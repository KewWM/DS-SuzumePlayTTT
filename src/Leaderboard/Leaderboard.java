package Leaderboard;

import java.io.*;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Leaderboard {
    private List<UserRecord> records;
    private static final int MAX_RECORDS = 10;

    public Leaderboard() {
        records = new LinkedList<>();
    }

    public void addUserRecord(String name, int score) {
        UserRecord record = new UserRecord(name, score);
        records.add(record);

        if (records.size() > MAX_RECORDS) {
            records.remove(0);
        }
    }

    public void displayLeaderboard(int type) {
        if (type == 1)
        {
            System.out.println("(ง︡'-'︠)ง ~~Leaderboard Normal Tic Tac Toe~~ (ง︡'-'︠)ง");
        }
        else if (type == 2)
        {
            System.out.println("(ง︡'-'︠)ง ~~Leaderboard Reversed Tic Tac Toe~~ (ง︡'-'︠)ง");
        }
        else if (type == 3)
        {
            System.out.println("(ง︡'-'︠)ง ~~Leaderboard Treblecrosss~~ (ง︡'-'︠)ง");
        }
        // Sort the records based on the highest to lowest score
        Collections.sort(records, Comparator.comparingInt(UserRecord::getScore).reversed()
                .thenComparing(UserRecord::getName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("┌───────────────────────────┬─────────┐");
        System.out.println("│        Leaderboard        │  Point  │");
        System.out.println("├───────────────────────────┼─────────┤");

        for (int i = 0; i < MAX_RECORDS; i++) {
            if (i < records.size()) {
                UserRecord record = records.get(i);
                String formattedName = String.format("%-26s", record.getName());
                String formattedScore = String.format("%-8s", record.getScore());
                System.out.printf("│ %s│ %s│\n", formattedName, formattedScore);
            } else {
                System.out.printf("│ %s│ %s│\n", String.format("%-26s", "Computer"), String.format("%-8s", 0));
            }
        }
        System.out.println("└───────────────────────────┴─────────┘");
    }

    public void saveLeaderboard(int type) {
        String fileName = " ";
        if (type == 1)
        {
            fileName = "./src/Leaderboard/normalTTTlb_pve.txt";
        }
        else if (type == 2)
        {
            fileName = "./src/Leaderboard/reversedTTTlb_pve.txt";
        }
        else if (type == 3)
        {
            fileName = "./src/Leaderboard/treblecrosslb_pve.txt";
        }
        else if (type == 4)
        {
            fileName = "./src/Leaderboard/normalTTTlb_pvp.txt";
        }
        else if (type == 5)
        {
            fileName = "./src/Leaderboard/reversedTTTlb_pvp.txt";
        }
        else if (type == 6)
        {
            fileName = "./src/Leaderboard/treblecrosslb_pvp.txt";
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (UserRecord record : records) {
                writer.println(record.getName() + "," + record.getScore());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the leaderboard.");
            System.exit(0);
        }
    }

    public void loadLeaderboard(int type) {
        String fileName = " ";
        if (type == 1)
        {
            fileName = "./src/Leaderboard/normalTTTlb_pve.txt";
        }
        else if (type == 2)
        {
            fileName = "./src/Leaderboard/reversedTTTlb_pve.txt";
        }
        else if (type == 3)
        {
            fileName = "./src/Leaderboard/treblecrosslb_pve.txt";
        }
        else if (type == 4)
        {
            fileName = "./src/Leaderboard/normalTTTlb_pvp.txt";
        }
        else if (type == 5)
        {
            fileName = "./src/Leaderboard/reversedTTTlb_pvp.txt";
        }
        else if (type == 6)
        {
            fileName = "./src/Leaderboard/treblecrosslb_pvp.txt";
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    addUserRecord(name, score);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading the leaderboard.");
            System.exit(0);
        }
    }

    public int normalTTTScore(int grade)
    {
        if (grade == 3)
        {
            return 100;
        }
        else if (grade == 4)
        {
            return 75;
        }
        else if (grade == 5)
        {
            return 50;
        }
        else
        {
            return 25;
        }
    }

    public int reversedTTTScore(int grade)
    {
        if (grade == 3)
        {
            return 100;
        }
        else if (grade == 4)
        {
            return 75;
        }
        else
        {
            return 50;
        }
    }

    public int treblecrossScore(int grade)
    {
        if (grade == 2)
        {
            return 100;
        }
        else if (grade == 3)
        {
            return 75;
        }
        else {
            return 50;
        }
    }

}
