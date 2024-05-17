package Treblecross;

import Leaderboard.Leaderboard;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class TreblecrossGameEvE extends Leaderboard {
    protected static final int BOARD_SIZE = 11;
    protected char[] board;
    protected char computer1Symbol;
    protected char computer2Symbol;
    protected Random random;
    protected Scanner scanner;
    protected int computer1Difficulty;
    protected int computer2Difficulty;
    protected LinkedList<Integer> listComputer1;
    protected LinkedList<Integer> listComputer2;
    protected String computer1Name;
    protected String computer2Name;
    public TreblecrossGameEvE() {
        board = new char[BOARD_SIZE];
        computer1Symbol = 'X';
        computer2Symbol = 'O';
        random = new Random();
        scanner = new Scanner(System.in);
        computer1Difficulty = 1;
        computer2Difficulty = 1;
        listComputer1 = new LinkedList<>();
        listComputer2 = new LinkedList<>();

        for (int i = 0; i < BOARD_SIZE; i++)
            board[i] = ' ';
    }

    protected void getComputerName() {
        System.out.print("Enter Player1's name: ");
        computer1Name = scanner.next();
        System.out.print("Enter Player2's name: ");
        computer2Name = scanner.next();
    }
    protected void play() {
        menu();
        System.out.println("Thanks for playing!");
    }

    protected void selectDifficulty() {
        System.out.println("\nSelect computer difficulty:\n1. Easy\n2. Medium\n3. Hard\n");
        while (true) {
            System.out.printf("Enter difficulty level (1-3) of %s: ", computer1Name);
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    computer1Difficulty = choice;
                    break;
                }
                System.out.println("Invalid choice! Please try again.");
            } else {
                String input = scanner.next();
                System.out.println("Invalid input! Please enter a valid choice.");
            }
        }
        while (true) {
            System.out.printf("Enter difficulty level (1-3) of %s: ", computer2Name);
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    computer2Difficulty = choice;
                    break;
                }
                System.out.println("Invalid choice! Please try again.");
            } else {
                String input = scanner.next();
                System.out.println("Invalid input! Please enter a valid choice.");
            }
        }
    }

    protected void menu()
    {
        System.out.println("╔════════════════════════════════════╗\n║    Welcome to Treblecross(EvE)!    ║\n╚════════════════════════════════════╝");
        System.out.print("1. Start a New Game!\n2. Exit Game\nSelect by entering (1-2): ");
        String menuChoice;
        while (true)
        {
            menuChoice = scanner.next();
            if (menuChoice.equals("1"))
            {
                getComputerName();
                selectDifficulty();

                // here
                playing();
                break;
            }
            else if (menuChoice.equals("2"))
            {
                System.out.println("Exiting Game... QAQ");
                break;
            }
            else
                System.out.print("Invalid Input! Please enter (1 - 2): ");
        }
    }

    protected void playing()
    {
        while (true)
        {
            printBoard();

            if (computer1Symbol == 'X') {
                makeComputerMove(computer1Symbol, computer1Name);
                System.out.printf("%s makes a move at [%s]!  ٩(⊙‿⊙)۶", computer1Name, (int)listComputer1.getLast());
            }
            else {
                makeComputerMove(computer2Symbol, computer2Name);
                System.out.printf("%s makes a move at [%s]!  ٩(⊙‿⊙)۶", computer2Name, (int)listComputer2.getLast());
            }

            if (isWinningMove(computer1Symbol))
            {
                printBoard();
                System.out.println("Computer1 wins!");
                break;
            }
            else if (isWinningMove(computer2Symbol))
            {
                printBoard();
                System.out.println("Computer2 wins!");
                break;
            }
            togglePlayer();
        }
    }

    protected void makeComputerMove(char symbol, String name) {
        if (name.equals(computer1Name))
        {
            System.out.println(computer1Name + "'s turn");
            int position;
            switch (computer1Difficulty) {
                case 1 -> {
                    position = getRandomMove();
                    makeMove(position, symbol);
                    listComputer1.add(position);
                }
                case 2 -> {
                    position = getMediumDifficultyMove();
                    makeMove(position, symbol);
                    listComputer1.add(position);
                }
                case 3 -> {
                    position = getHardDifficultyMove();
                    makeMove(position, symbol);
                    listComputer1.add(position);
                }
            }
        }
        else if (name.equals(computer2Name))
        {
            System.out.println(computer2Name + "'s turn");
            int position;
            switch (computer2Difficulty) {
                case 1 -> {
                    position = getRandomMove();
                    makeMove(position, symbol);
                    listComputer2.add(position);
                }
                case 2 -> {
                    position = getMediumDifficultyMove();
                    makeMove(position, symbol);
                    listComputer2.add(position);
                }
                case 3 -> {
                    position = getHardDifficultyMove();
                    makeMove(position, symbol);
                    listComputer2.add(position);
                }
            }
        }
    }

    protected int getRandomMove() {
        int position;
        do {
            position = random.nextInt(BOARD_SIZE);
        } while (!isValidMove(position));
        return position;
    }

    protected int getMediumDifficultyMove() {
        // Check if opponent can win with the next move
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (isValidMove(i)) {
                makeMove(i, computer1Symbol);
                if (isWinningMove(computer1Symbol)) {
                    board[i] = ' '; // Undo the move
                    return i;
                }
                board[i] = ' '; // Undo the move
            }
        }

        // Check if self can win with the next move
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (isValidMove(i)) {
                makeMove(i, computer2Symbol);
                if (isWinningMove(computer2Symbol)) {
                    board[i] = ' '; // Undo the move
                    return i;
                }
                board[i] = ' '; // Undo the move
            }
        }

        // If neither player can win, make a random move
        return getRandomMove();
    }

    protected int getHardDifficultyMove() {
        // Check if self can win with the next move
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (isValidMove(i)) {
                makeMove(i, computer2Symbol);
                if (isWinningMove(computer2Symbol)) {
                    board[i] = ' '; // Undo the move
                    return i;
                }
                board[i] = ' '; // Undo the move
            }
        }

        // Check if opponent can win with the next move
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (isValidMove(i)) {
                makeMove(i, computer1Symbol);
                if (isWinningMove(computer1Symbol)) {
                    board[i] = ' '; // Undo the move
                    return i;
                }
                board[i] = ' '; // Undo the move
            }
        }

        // If neither player can win, make a random move
        return getRandomMove();
    }

    protected void makeMove(int position, char symbol) {
        board[position] = symbol;
    }

    protected boolean isValidMove(int position) {
        return (position >= 0 && position < BOARD_SIZE && board[position] == ' ');
    }

    protected boolean isWinningMove(char symbol) {
        for (int i = 0; i < BOARD_SIZE - 2; i++) {
            if (board[i] == symbol && board[i + 1] == symbol && board[i + 2] == symbol) {
                return true;
            }
        }
        return false;
    }

    protected void togglePlayer() { //Swap the symbol of both players
        char temp = computer1Symbol;
        computer1Symbol = computer2Symbol;
        computer2Symbol = temp;
    }

    protected void printBoard()
    {
        System.out.println("\n  0   1   2   3   4   5   6   7   8   9  10");
        System.out.println("┌───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┐");
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            System.out.print("│ " + board[i] + " ");
        }
        System.out.println("│");
        System.out.println("└───┴───┴───┴───┴───┴───┴───┴───┴───┴───┴───┘");
    }
}
