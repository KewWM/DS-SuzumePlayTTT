package Treblecross;

import Leaderboard.Leaderboard;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class TreblecrossGamePvE {
    private Leaderboard lb = new Leaderboard();
    protected static final int BOARD_SIZE = 11;
    protected char[] board;
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

    public TreblecrossGamePvE() {
        board = new char[BOARD_SIZE];
        player1Symbol = 'X';
        player2Symbol = 'O';
        random = new Random();
        scanner = new Scanner(System.in);
        computerDifficulty = 1;
        listPlayer1 = new LinkedList<>();
        listPlayer2 = new LinkedList<>();
        loadingList = new LinkedList<>();
        undoCount = 0;
        grade = 0;

        for (int i = 0; i < BOARD_SIZE; i++)
            board[i] = ' ';
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
                String input = scanner.next();
                System.out.println("Invalid input! Please enter a valid choice.");
            }
        }
    }

    protected void menu()
    {
        System.out.println("╔════════════════════════════════════╗\n║    Welcome to Treblecross(PvE)!    ║\n╚════════════════════════════════════╝");
        System.out.print("1. Start a New Game!\n2. Load Game\n3. Exit Game\nSelect by entering (1-3): ");
        String menuChoice;
        while (true)
        {
            menuChoice = scanner.next();
            if (menuChoice.equals("1"))
            {
                getPlayer1Name();
                selectDifficulty();
                playing();
                break;
            }
            else if (menuChoice.equals("2"))
            {
                loadGame();
                break;
            }
            else if (menuChoice.equals("3"))
            {
                System.out.println("Exiting Game... QAQ");
                break;
            }
            else
                System.out.print("Invalid Input! Please enter (1 - 3): ");
        }
    }

    protected void playing()
    {
        while (true)
        {
            printBoard();

            if (player1Symbol == 'X')
                makePlayerMove(player1Symbol, player1Name);
            else
                makeComputerMove(player2Symbol);

            if (isWinningMove(player1Symbol))
            {
                printBoard();
                System.out.println(player1Name + " wins!");
                grade = listPlayer1.size() + undoCount;
                lb.loadLeaderboard(3);
                lb.addUserRecord(player1Name, lb.treblecrossScore(grade));
                lb.saveLeaderboard(3);
                lb.displayLeaderboard(3);
                break;
            }
            else if (isWinningMove(player2Symbol))
            {
                printBoard();
                System.out.println("Computer wins!");
                grade = listPlayer2.size();
                lb.loadLeaderboard(3);
                lb.addUserRecord("Computer Treblecross", lb.treblecrossScore(grade));
                lb.saveLeaderboard(3);
                lb.displayLeaderboard(3);
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
            makeMove(position, symbol);
            if (playerName.equals(player1Name))
                listPlayer1.add(position);
        }
    }

    protected void undo()
    {
        System.out.println("\n-=≡Σ(((⊃ﾟ∀ﾟ)つ Undo Previous move!!! -=≡Σ(((⊃ﾟ∀ﾟ)つ");
        int temp1 = listPlayer1.getLast();
        int temp2 = listPlayer2.getLast();
        listPlayer1.removeLast();
        listPlayer2.removeLast();
        makeMove(temp1, ' ');
        makeMove(temp2, ' ');
        printBoard();
    }

    protected int getPlayerMove()
    {
        String input;
        while (true)
        {
            System.out.print("Enter position (0-" + (BOARD_SIZE - 1) + ") or 'exit' to quit or 'undo' move: ");
            input = scanner.next();
            if (isValidMove(input)) { //User make a move
                int position = Integer.parseInt(input);
                if (isValidMove(position)) {
                    return position;
                }
                else {
                    System.out.println("The position is occupied!");
                }
            }
            else if (input.equals("exit"))
            { //User exit the game, can save the game if want to
                System.out.print("Before quiting, do you want to save the game? (yes/no): ");
                input = scanner.next();
                while (true)
                {
                    if (input.equals("yes"))
                    {
                        saveGame();
                        break;
                    }
                    else if (input.equals("no"))
                    {
                        break;
                    }
                    System.out.print("Invalid input! Please enter (yes/no): ");
                    input = scanner.next();
                }
                return -1;
            }
            else if (input.equals("undo"))
            {
                undoCount++;
                if(undoCount <= 1)
                    return -2;
                else
                {
                    System.out.println("(╬≖_≖) Sorry, you can only undo once in a game!!!");
                    printBoard();
                }
            }
            else
            {
                System.out.println("Invalid input! Please enter a valid position or 'exit' to quit or 'undo' move: ");
            }
        }
    }

    protected boolean isValidMove(String input) { //Check if the position of board is filled
        try {
            int position = Integer.parseInt(input);
            return (position >= 0 && position < BOARD_SIZE);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected void makeComputerMove(char symbol) {
        System.out.println("Computer's turn.");
        int position;
        switch (computerDifficulty) {
            case 1 -> {
                position = getRandomMove();
                makeMove(position, symbol);
                listPlayer2.add(position);
            }
            case 2 -> {
                position = getMediumDifficultyMove();
                makeMove(position, symbol);
                listPlayer2.add(position);
            }
            case 3 -> {
                position = getHardDifficultyMove();
                makeMove(position, symbol);
                listPlayer2.add(position);
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
        // Check if player 1 can win with the next move
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (isValidMove(i)) {
                makeMove(i, player1Symbol);
                if (isWinningMove(player1Symbol)) {
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
        // Check if computer can win with the next move
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (isValidMove(i)) {
                makeMove(i, player2Symbol);
                if (isWinningMove(player2Symbol)) {
                    board[i] = ' '; // Undo the move
                    return i;
                }
                board[i] = ' '; // Undo the move
            }
        }

        // Check if player 1 can win with the next move
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (isValidMove(i)) {
                makeMove(i, player1Symbol);
                if (isWinningMove(player1Symbol)) {
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
        char temp = player1Symbol;
        player1Symbol = player2Symbol;
        player2Symbol = temp;
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
                    makeMove(position, player1Symbol);
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
                    makeMove(position, player1Symbol);
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
