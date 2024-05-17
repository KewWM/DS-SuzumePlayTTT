package NormalTTT;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class NormalTTT_EvE {
    protected static final int BOARD_SIZE = 5;
    protected char[][] board;
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
    protected char Symbol;

    public NormalTTT_EvE() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        computer1Symbol = 'X';
        computer2Symbol = 'O';
        Symbol = 'X';
        random = new Random();
        scanner = new Scanner(System.in);
        computer1Difficulty = 1;
        computer2Difficulty = 1;
        listComputer1 = new LinkedList<>();
        listComputer2 = new LinkedList<>();

        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = ' ';
    }

    protected void getComputerName() {
        System.out.print("Enter Computer1's name: ");
        computer1Name = scanner.next();
        System.out.print("Enter Computer2's name: ");
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
                System.out.println("Invalid input! Please enter a valid choice.");
                scanner.next();
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
                System.out.println("Invalid input! Please enter a valid choice.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    protected void menu() {
        System.out.println("╔═════════════════════════════════════╗\n║    Welcome to Tic Tac Toe (EvE)!    ║\n╚═════════════════════════════════════╝");
        System.out.print("1. Start a New Game!\n2. Exit Game\nSelect by entering (1-2): ");
        while (true) {
            try {
                int menuChoice = scanner.nextInt();
                switch (menuChoice) {
                    case 1:
                        getComputerName();
                        selectDifficulty();
                        playing();
                        System.exit(0);
                        break;
                    case 2:
                        System.out.println("Exiting Game... QAQ");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid option.");
                scanner.nextLine(); // Consume the invalid input to prevent an infinite loop
            }
        }
    }

    protected void playing() {
        while (true) {
            printBoard();

            if (Symbol == 'X') {
                makeComputerMove(computer1Symbol, computer1Name);
                if(!listComputer1.isEmpty())
                    System.out.printf("%s makes a move at [%s]!  ٩(⊙‿⊙)۶", computer1Name, listComputer1.getLast());
            } else {
                makeComputerMove(computer2Symbol, computer2Name);
                if(!listComputer2.isEmpty())
                    System.out.printf("%s makes a move at [%s]!  ٩(⊙‿⊙)۶", computer2Name, listComputer2.getLast());
            }

            if (isWinningMove(computer1Symbol)) {
                printBoard();
                System.out.println((computer1Name) +" (Computer1) wins!");
                break;
            } else if (isWinningMove(computer2Symbol)) {
                printBoard();
                System.out.println(computer2Name +" (Computer2) wins!");
                break;
            }
            togglePlayer();
        }
    }


    protected void makeComputerMove(char symbol, String name) {
        if (name.equals(computer1Name)) {
            System.out.println(computer1Name + "'s turn");
            int position;
            switch (computer1Difficulty) {
                case 1:
                    position = getRandomMove();
                    int row = position / BOARD_SIZE;
                    int column = position % BOARD_SIZE;
                    makeMove(row, column, symbol);
                    listComputer1.add(position);
                    break;
                case 2:
                    position = getMediumDifficultyMove();
                    row = position / BOARD_SIZE;
                    column = position % BOARD_SIZE;
                    makeMove(row, column, symbol);
                    listComputer1.add(position);
                    break;
                case 3:
                    position = getHardDifficultyMove();
                    row = position / BOARD_SIZE;
                    column = position % BOARD_SIZE;
                    makeMove(row, column, symbol);
                    listComputer1.add(position);
                    break;
            }
        } else if (name.equals(computer2Name)) {
            System.out.println(computer2Name + "'s turn");
            int position;
            switch (computer2Difficulty) {
                case 1:
                    position = getRandomMove();
                    int row = position / BOARD_SIZE;
                    int column = position % BOARD_SIZE;
                    makeMove(row, column, symbol);
                    listComputer2.add(position);
                    break;
                case 2:
                    position = getMediumDifficultyMove();
                    row = position / BOARD_SIZE;
                    column = position % BOARD_SIZE;
                    makeMove(row, column, symbol);
                    listComputer2.add(position);
                    break;
                case 3:
                    position = getHardDifficultyMove();
                    row = position / BOARD_SIZE;
                    column = position % BOARD_SIZE;
                    makeMove(row, column, symbol);
                    listComputer2.add(position);
                    break;
            }
        }
    }


    protected int getRandomMove() {
        int row, column;
        do {
            row = random.nextInt(BOARD_SIZE);
            column = random.nextInt(BOARD_SIZE);
        } while (!isValidMove(row, column));
        return row * BOARD_SIZE + column; // Convert row and column indices to a single position
    }

    protected int getHardDifficultyMove() {
        // Check if computer can win with the next move
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isValidMove(i, j)) {
                    makeMove(i, j, computer2Symbol);
                    if (isWinningMove(computer2Symbol)) {
                        board[i][j] = ' '; // Undo the move
                        return i * BOARD_SIZE + j;
                    }
                    board[i][j] = ' '; // Undo the move
                }
            }
        }
        // Check if player 1 can win with the next move
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isValidMove(i, j)) {
                    makeMove(i, j, computer1Symbol);
                    if (isWinningMove(computer1Symbol)) {
                        board[i][j] = ' '; // Undo the move
                        return i * BOARD_SIZE + j;
                    }
                    board[i][j] = ' '; // Undo the move
                }
            }
        }
        return getRandomMove();
    }

    protected int getMediumDifficultyMove(){
        int bestScore = 0;
        int bestMove = -1;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isValidMove(i, j)) {
                    makeMove(i, j, computer2Symbol);
                    int score = minimax(false, 8, 0, 10000);
                    board[i][j] = ' '; // Undo the move

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i * BOARD_SIZE + j;
                    }
                }
            }
        }

        return (bestMove != -1) ? bestMove : getRandomMove();
    }

    private int minimax(boolean isMaximizingPlayer, int depth, int alpha, int beta) {
        if (isWinningMove(computer2Symbol)) {
            return Integer.MAX_VALUE - depth; // Computer wins
        } else if (isWinningMove(computer1Symbol)) {
            return Integer.MIN_VALUE + depth; // Player wins
        } else if (isBoardFull()) {
            return 0; // Draw
        }

        if (isMaximizingPlayer) {
            int bestScore = 0;

            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (isValidMove(i, j)) {
                        makeMove(i, j, computer2Symbol);
                        int score = minimax(false, depth + 1, alpha, beta);
                        board[i][j] = ' '; // Undo the move
                        bestScore = Math.max(score, bestScore);
                        alpha = Math.max(alpha, bestScore);
                        if (alpha >= beta) {
                            return bestScore; // Alpha cut-off
                        }
                    }
                }
            }

            return bestScore;
        } else {
            int bestScore = 10000;

            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (isValidMove(i, j)) {
                        makeMove(i, j, computer1Symbol);
                        int score = minimax(true, depth + 1, alpha, beta);
                        board[i][j] = ' '; // Undo the move
                        bestScore = Math.min(score, bestScore);
                        beta = Math.min(beta, bestScore);
                        if (alpha >= beta) {
                            return bestScore; // Beta cut-off
                        }
                    }
                }
            }

            return bestScore;
        }
    }

    protected boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == '-') {
                    return false; // Found an empty cell, board is not full
                }
            }
        }
        return true; // All cells are filled, board is full
    }

    protected void makeMove(int row, int column, char symbol) {
        board[row][column] = symbol;
    }

    protected boolean isValidMove(int i, int j) {
        return (i >= 0 && i < BOARD_SIZE && j >= 0 && j < BOARD_SIZE && board[i][j] == ' ');
    }

    protected boolean isWinningMove(char symbol) {
        // column wins
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[0][j] == symbol && board[1][j] == symbol && board[2][j] == symbol ||
                    board[1][j] == symbol && board[2][j] == symbol && board[3][j] == symbol ||
                    board[2][j] == symbol && board[3][j] == symbol && board[4][j] == symbol) {
                return true;
            }
        }

        // row wins
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol ||
                    board[i][1] == symbol && board[i][2] == symbol && board[i][3] == symbol ||
                    board[i][2] == symbol && board[i][3] == symbol && board[i][4] == symbol) {
                return true;
            }
        }

        // Check for diagonal
        for (int i = 0; i < BOARD_SIZE - 2; i++) {
            if (board[i][0] == symbol && board[i + 1][1] == symbol && board[i + 2][2] == symbol ||
                    board[i][1] == symbol && board[i + 1][2] == symbol && board[i + 2][3] == symbol ||
                    board[i][2] == symbol && board[i + 1][3] == symbol && board[i + 2][4] == symbol ||
                    board[i][4] == symbol && board[i + 1][3] == symbol && board[i + 2][2] == symbol ||
                    board[i][3] == symbol && board[i + 1][2] == symbol && board[i + 2][1] == symbol ||
                    board[i][2] == symbol && board[i + 1][1] == symbol && board[i + 2][0] == symbol) {
                return true;
            }
        }
        return false;
    }


    protected void togglePlayer() { //Swap the symbol of both players
        if (Symbol == 'X')
            Symbol = 'O';
        else
            Symbol = 'X';
    }

    protected void printBoard() {
        System.out.println("\n   TicTacToe (E vs E)");
        System.out.println(" ┌───┬───┬───┬───┬───┐");
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (i > 0)
                System.out.println(" ├───┼───┼───┼───┼───┤");

            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(" │ " + board[i][j]);
            }
            System.out.println(" │");
        }
        System.out.println(" └───┴───┴───┴───┴───┘");
    }
}
