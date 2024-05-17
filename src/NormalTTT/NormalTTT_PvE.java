package NormalTTT;

import Leaderboard.Leaderboard;
import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class NormalTTT_PvE {
    private Leaderboard lb = new Leaderboard();
    protected static final int BOARD_SIZE = 5;
    protected char[][] board;
    protected char player1Symbol;
    protected char player2Symbol;
    protected Random random;
    protected Scanner scanner;
    protected int computerDifficulty;
    protected LinkedList<Integer> listPlayer1;
    protected LinkedList<Integer> listPlayer2;
    protected LinkedList<String> loadingList;
    protected String player1Name;
    protected int undoCount;
    protected int grade;
    protected char Symbol;

    public NormalTTT_PvE() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        player1Symbol = 'X';
        player2Symbol = 'O';
        random = new Random();
        scanner = new Scanner(System.in);
        computerDifficulty = 1;
        Symbol = 'X';
        listPlayer1 = new LinkedList<>();
        listPlayer2 = new LinkedList<>();
        loadingList = new LinkedList<>();
        undoCount = 0;
        grade = 0;

        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = ' ';
    }

    protected void play() {
        menu();
        System.out.println("Thanks for playing!");
    }

    protected void getPlayer1Name() {
        System.out.print("Enter Player1's name: ");
        player1Name = scanner.next();
    }

    protected void selectDifficulty() {
        System.out.println("\nSelect computer difficulty:\n1. Easy\n2. Medium\n3. Hard\n");
        while (true) {
            System.out.print("Enter difficulty level (1-3): ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    computerDifficulty = choice;
                    break;
                }
                System.out.println("Invalid choice! Please try again.");
            } else {
                System.out.println("Invalid input! Please enter a valid choice.");
                scanner.next();
            }
        }
    }

    protected void menu() {
        System.out.println("╔═════════════════════════════════════╗\n║    Welcome to Tic Tac Toe (PvE)!    ║\n╚═════════════════════════════════════╝");
        System.out.print("1. Start a New Game!\n2. Load Game\n3. Exit Game\nSelect by entering (1-3): ");

        while (true) {
            try {
                int menuChoice = scanner.nextInt();
                switch (menuChoice) {
                    case 1:
                        getPlayer1Name();
                        selectDifficulty();
                        playing();
                        System.exit(0);
                        break;
                    case 2:
                        loadGame();
                        System.exit(0);
                        break;
                    case 3:
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

            if (Symbol == 'X')
            {
                makePlayerMove(player1Symbol, player1Name);
            }
            else {
                makeComputerMove(player2Symbol);
            }

            if (isWinningMove(player1Symbol)) {
                printBoard();
                System.out.println(player1Name + " wins!");
                grade = listPlayer1.size() + undoCount;
                lb.loadLeaderboard(1);
                lb.addUserRecord(player1Name, lb.normalTTTScore(grade));
                lb.saveLeaderboard(1);
                lb.displayLeaderboard(1);
                break;
            }
            else if (isWinningMove(player2Symbol)) {
                printBoard();
                System.out.println("Computer wins!");
                grade = listPlayer2.size();
                lb.loadLeaderboard(1);
                lb.addUserRecord("Computer NormalTTT", lb.normalTTTScore(grade));
                lb.saveLeaderboard(1);
                lb.displayLeaderboard(1);
                break;
            }
            togglePlayer();
        }
    }

    protected void makePlayerMove(char symbol, String playerName) {
        System.out.println(playerName + "'s turn.");
        int position = getPlayerMove();
        if (position == -2) {
            undo();
            //Recursion: Prompt user to make next move after undo
            makePlayerMove(symbol, playerName);
        } else if (position == -1) { //End the game
            System.out.println(playerName + " exits the game.");
            System.exit(0);
        } else {
            int row = position / BOARD_SIZE;
            int column = position % BOARD_SIZE;
            makeMove(row, column, symbol);
            if (playerName.equals(player1Name))
                listPlayer1.add(position);
        }
    }

    protected void undo() {
        System.out.println("\n-=≡Σ(((⊃ﾟ∀ﾟ)つ Undo Previous move!!! -=≡Σ(((⊃ﾟ∀ﾟ)つ");
        int temp1 = listPlayer1.getLast();
        int temp2 = listPlayer2.getLast();
        listPlayer1.removeLast();
        listPlayer2.removeLast();
        int row1 = temp1 / BOARD_SIZE;
        int column1 = temp1 % BOARD_SIZE;
        int row2 = temp2 / BOARD_SIZE;
        int column2 = temp2 % BOARD_SIZE;
        makeMove(row1, column1, ' ');
        makeMove(row2, column2, ' ');
        printBoard();
    }

    protected int getPlayerMove() {
        String input;
        while (true) {
            System.out.print("Enter position (0-" + (BOARD_SIZE - 1) + ") in (x,y) or 'exit' to quit or 'undo' move: ");
            input = scanner.next();
            if (isValidMove(input)) { //User make a move
                String[] location = input.split(",");
                int[] intArray = new int[2];
                intArray[0] = Integer.parseInt(location[0]);
                intArray[1] = Integer.parseInt(location[1]);
                int position = intArray[0] * 5 + intArray[1];
                return position;
            }
            else if (input.equals("exit")) { //User exit the game, can save the game if want to
                System.out.print("Before quiting, do you want to save the game? (yes/no): ");
                input = scanner.next();
                while (true) {
                    if (input.equals("yes")) {
                        saveGame();
                        break;
                    } else if (input.equals("no")) {
                        break;
                    }
                    System.out.print("Invalid input! Please enter (yes/no): ");
                    input = scanner.next();
                }
                return -1;
            } else if (input.equals("undo")) {
                undoCount++;
                if (undoCount <= 1)
                    return -2;
                else {
                    System.out.println("(╬≖_≖) Sorry, you can only undo once in a game!!!");
                    printBoard();
                }
            } else {
                System.out.println("Invalid input! Please enter a valid position or 'exit' to quit or 'undo' move: ");
            }
        }
    }

    protected boolean isValidMove(String input) {
        String[] location = input.split(",");
        if (location.length != 2) {
            return false; // Invalid input format
        }
        try {
            int[] intArray = new int[2];
            intArray[0] = Integer.parseInt(location[0]);
            intArray[1] = Integer.parseInt(location[1]);

            // Check if the coordinates are within the board's bounds
            if (intArray[0] >= 0 && intArray[0] < board.length &&
                    intArray[1] >= 0 && intArray[1] < board[0].length) {
                if (board[intArray[0]][intArray[1]] == ' ')
                    return true;
                }
            } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid position or 'exit' to quit or 'undo' move: ");
            }
        return false;
    }

    protected void makeComputerMove(char symbol) {
        System.out.println("Computer's turn.");
        int position;
        switch (computerDifficulty) {
            case 1 :
                position = getRandomMove();
                int row = position / BOARD_SIZE;
                int column = position % BOARD_SIZE;
                makeMove(row, column, symbol);
                listPlayer2.add(position);
                break;
            case 2 :
                position = getMediumDifficultyMove();
                row = position / BOARD_SIZE;
                column = position % BOARD_SIZE;
                makeMove(row, column, symbol);
                listPlayer2.add(position);
                break;
            case 3 :
                position = getHardDifficultyMove();
                row = position / BOARD_SIZE;
                column = position % BOARD_SIZE;
                makeMove(row, column, symbol);
                listPlayer2.add(position);
                break;
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
                    makeMove(i, j, player2Symbol);
                    if (isWinningMove(player2Symbol)) {
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
                    makeMove(i, j, player1Symbol);
                    if (isWinningMove(player1Symbol)) {
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
                    makeMove(i, j, player2Symbol);
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
        if (isWinningMove(player2Symbol)) {
            return Integer.MAX_VALUE - depth; // Computer wins
        } else if (isWinningMove(player1Symbol)) {
            return Integer.MIN_VALUE + depth; // Player wins
        } else if (isBoardFull()) {
            return 0; // Draw
        }

        if (isMaximizingPlayer) {
            int bestScore = 0;

            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (isValidMove(i, j)) {
                        makeMove(i, j, player2Symbol);
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
                        makeMove(i, j, player1Symbol);
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

    private boolean isBoardFull() {
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


    protected void printBoard() {
        System.out.println("\n   TicTacToe (P vs E)");
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

    protected void togglePlayer() { //Swap the symbol of both players
        if (Symbol == 'X')
            Symbol = 'O';
        else
            Symbol = 'X';
    }
    protected void saveGame()
    {
        System.out.print("Enter the file name to save the game: ");
        String fileName = scanner.next() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            //Difficulty:
            writer.write(String.valueOf(computerDifficulty));
            writer.newLine();
            //Player 1:
            writer.write(player1Name);
            writer.newLine();

            //Moves Player 1:
            if (listPlayer1.isEmpty())
                writer.write("0");
            else
            {
                for (int move : listPlayer1)
                {
                    writer.write(move + " ");
                }
            }
            writer.newLine();

            //Moves Player 2:
            if (listPlayer2.isEmpty())
                writer.write("No moves");
            else
            {
                for (int move : listPlayer2)
                {
                    writer.write(move + " ");
                }
            }
            writer.newLine();

            //Determine this game use undo once or not
            writer.write(String.valueOf(undoCount));
            writer.newLine();

            System.out.println("Game saved successfully!");
        }
        catch (IOException e)
        {
            System.out.println("Error occurred while saving the game: " + e.getMessage());
        }
    }

    protected void loadGame()
    {
        System.out.print("Enter the file name to load the game: ");
        String fileName = scanner.next() + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            int lineCount = 1;
            while ((line = reader.readLine()) != null)
            {
                loadingList.add(line);
                lineCount++;
            }
            reader.close();
            System.out.println("Game Loaded Successfully...");

            // Set the computer difficulty
            int difficulty = Integer.parseInt(loadingList.get(0));
            computerDifficulty = difficulty;

            // Set the player name
            player1Name = loadingList.get(1);

            // Restore player 1's moves
            String player1Moves = loadingList.get(2);
            if (!player1Moves.equals("No moves"))
            {
                String[] moves = player1Moves.split(" ");
                for (String move : moves)
                {
                    int position = Integer.parseInt(move);
                    listPlayer1.add(position);
                    makeMove(position / 5, position % 5, player1Symbol);
                }
            }

            // Restore player 2's moves
            String player2Moves = loadingList.get(3);
            if (!player2Moves.equals("No moves"))
            {
                String[] moves = player2Moves.split(" ");
                for (String move : moves)
                {
                    int position = Integer.parseInt(move);
                    listPlayer2.add(position);
                    makeMove(position / 5, position % 5, player2Symbol);
                }
            }

            //Restore undoCount
            undoCount = Integer.parseInt(loadingList.get(4));

            // Continue the game from the loaded state
            playing();

        }
        catch (IOException e)
        {
            System.out.print("Error occurred while loading the game! Load again? (yes/no): ");
            String yesNo = scanner.next();
            if (yesNo.equals("yes"))
                loadGame();
            else
                System.exit(0);
        }
    }
}
