package NormalTTT;

import Leaderboard.Leaderboard;
import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class NormalTTT_PvP {
    private Leaderboard lb = new Leaderboard();
    protected static final int BOARD_SIZE = 5;
    protected char[][] board;
    protected char player1Symbol;
    protected char player2Symbol;
    protected Scanner scanner;
    protected int computerDifficulty;
    protected LinkedList<Integer> listPlayer1;
    protected LinkedList<Integer> listPlayer2;
    protected LinkedList<String> loadingList;
    protected String player1Name;
    protected String player2Name;
    protected int undoCount1;
    protected int undoCount2;
    protected int grade;
    protected char Symbol;

    public NormalTTT_PvP() {

        board = new char[BOARD_SIZE][BOARD_SIZE];
        player1Symbol = 'X';
        player2Symbol = 'O';
        scanner = new Scanner(System.in);
        computerDifficulty = 1;
        Symbol = 'X';
        listPlayer1 = new LinkedList<>();
        listPlayer2 = new LinkedList<>();
        loadingList = new LinkedList<>();
        undoCount1 = 0;
        undoCount2 = 0;
        grade = 0;

        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = ' ';
    }

    protected void play() {
        menu();
        System.out.println("Thanks for playing!");
    }

    protected void getPlayerName() {
        System.out.print("Enter Player1's name: ");
        player1Name = scanner.next();
        System.out.print("Enter Player2's name: ");
        player2Name = scanner.next();
    }

    protected void menu() {
        System.out.println("╔═════════════════════════════════════╗\n║    Welcome to Tic Tac Toe (PvP)!    ║\n╚═════════════════════════════════════╝");
        System.out.print("1. Start a New Game!\n2. Load Game\n3. Exit Game\nSelect by entering (1-3): ");

        while (true) {
            try {
                int menuChoice = scanner.nextInt();
                switch (menuChoice) {
                    case 1:
                        getPlayerName();
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
                makePlayerMove(player2Symbol, player2Name);
            }


            if (isWinningMove(player1Symbol)) {
                printBoard();
                System.out.println(player1Name + " wins!");
                grade = listPlayer1.size() + undoCount1;
                lb.loadLeaderboard(4);
                lb.addUserRecord(player1Name, lb.normalTTTScore(grade));
                lb.saveLeaderboard(4);
                lb.displayLeaderboard(4);
                break;
            } else if (isWinningMove(player2Symbol)) {
                printBoard();
                System.out.println(player2Name + " wins!");
                grade = listPlayer2.size() + undoCount2;
                lb.loadLeaderboard(4);
                lb.addUserRecord(player2Name, lb.normalTTTScore(grade));
                lb.saveLeaderboard(4);
                lb.displayLeaderboard(4);
                break;
            }
            togglePlayer();
        }
    }

    protected void makePlayerMove(char symbol, String playerName) {
        System.out.println(playerName + "'s turn.");
        int position = getPlayerMove(playerName);
        if (position == -2) {
            undo(playerName);
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
            else if (playerName.equals(player2Name))
                listPlayer2.add(position);
        }
    }

    protected void undo(String player) {
        if (player.equals(player1Name)) {
            int temp1, temp2;
            temp1 = listPlayer1.getLast();
            temp2 = listPlayer2.getLast();
            listPlayer1.removeLast();
            listPlayer2.removeLast();
            int row1 = temp1 / BOARD_SIZE;
            int column1 = temp1 % BOARD_SIZE;
            int row2 = temp2 / BOARD_SIZE;
            int column2 = temp2 % BOARD_SIZE;
            makeMove(row1, column1, ' ');
            makeMove(row2, column2, ' ');
        } else if (player.equals(player2Name)) {
            int temp1, temp2;
            temp2 = listPlayer2.getLast();
            temp1 = listPlayer1.getLast();
            listPlayer2.removeLast();
            listPlayer1.removeLast();
            int row1 = temp1 / BOARD_SIZE;
            int column1 = temp1 % BOARD_SIZE;
            int row2 = temp2 / BOARD_SIZE;
            int column2 = temp2 % BOARD_SIZE;
            makeMove(row2, column2, ' ');
            makeMove(row1, column1, ' ');
        }
        System.out.println("\n-=≡Σ(((⊃ﾟ∀ﾟ)つ Undo Previous move!!! -=≡Σ(((⊃ﾟ∀ﾟ)つ");
        printBoard();
    }

    protected int getPlayerMove(String name) {
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
            } else if (input.equals("exit")) { //User exit the game, can save the game if want to
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
                if (name.equals(player1Name) && !listPlayer1.isEmpty()) {
                    if (undoCount1 < 1) {
                        undoCount1++;
                        return -2;
                    } else {
                        System.out.printf("(╬≖_≖) Sorry %s, you can only undo once in a game!!!\n", player1Name);
                        printBoard();
                    }
                } else if (name.equals(player2Name) && !listPlayer2.isEmpty()) {

                    if (undoCount2 < 1) {
                        undoCount2++;
                        return -2;
                    } else {
                        System.out.printf("(╬≖_≖) Sorry %s, you can only undo once in a game!!!\n", player2Name);
                        printBoard();
                    }
                } else {
                    System.out.println("(☞◣д◢)☞ You can't undo in the first round!");
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

    protected void makeMove(int row, int column, char symbol) {
        board[row][column] = symbol;
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
        System.out.println("\n   Tic Tac Toe (P vs P)");
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
            //Player 2:
            writer.write(player2Name);
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

            //Determine player1 use undo once or not
            writer.write(String.valueOf(undoCount1));
            writer.newLine();
            //Determine player2 game use undo once or not
            writer.write(String.valueOf(undoCount2));
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
            player2Name = loadingList.get(2);

            // Restore player 1's moves
            String player1Moves = loadingList.get(3);
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
            String player2Moves = loadingList.get(4);
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
            undoCount1 = Integer.parseInt(loadingList.get(5));
            undoCount2 = Integer.parseInt(loadingList.get(6));

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
    protected void togglePlayer() { //Swap the symbol of both players
        if (Symbol == 'X')
            Symbol = 'O';
        else
            Symbol = 'X';
    }
}
