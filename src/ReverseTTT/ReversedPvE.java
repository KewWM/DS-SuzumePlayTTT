package ReverseTTT;

import Leaderboard.Leaderboard;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class ReversedPvE {
    private Leaderboard lb = new Leaderboard();
    private static final int boardSize = 9;
    private char[] board;
    private char player1;
    private char player2;
    private char Symbol;
    private Random rand;
    private Scanner scan;
    private int compDiffLvl;
    private LinkedList<Integer> listPlayer1Move;
    private LinkedList<Integer> listPlayer2Move;
    private LinkedList<String> uploadList;
    private String playerName;
    private int undoCount;
    protected int grade;
    public String menuChoice;

    public ReversedPvE() {
        board = new char[boardSize];
        player1 = 'X';
        player2 = 'O';
        rand = new Random();
        scan = new Scanner(System.in);
        compDiffLvl = 1;
        listPlayer1Move = new LinkedList<>();
        listPlayer2Move = new LinkedList<>();
        uploadList = new LinkedList<>();
        undoCount = 0;
        grade = 0;

        int start = rand.nextInt(6);
        if (start <= 2) {
            Symbol = 'X';
        } else {
            Symbol = 'O';
        }

        for (int i = 0; i < boardSize; i++) {
            board[i] = ' ';
        }
    }

    public void printingBoard() {
        System.out.println("_____________");
        System.out.println("╔═══╦═══╦═══╗");
        for (int i = 0; i < board.length; i++) {
            System.out.print("║");
            System.out.print(" " + board[i] + " ");
            if ((i + 1) % 3 != 0) {
                System.out.print("");
            } else if (i != board.length - 1) {
                System.out.print("║");
                System.out.println("\n╠═══╬═══╬═══╣");
            }
        }
        System.out.println("║");
        System.out.println("╚═══╩═══╩═══╝");
        System.out.println("_____________");
        System.out.println("");

    }

    public void getPlayerName() {
        System.out.print("Enter Player1's name: ");
        playerName = scan.next();
    }

    public void chooseDifficulty() {
        System.out.println("\nSelect computer difficulty:\n1. Easy\n2. Medium\n3. Hard\n");

        while (true) {
            System.out.print("Enter difficulty level (1-3): ");
            if (scan.hasNextInt()) {
                int choice = scan.nextInt();
                if (choice >= 1 && choice <= 3) {
                    compDiffLvl = choice;
                    break;
                }
                System.out.println("Invalid choice! Please try again.");
            } else {
                String input = scan.next();
                System.out.println("Invalid input! Please enter a valid choice.");
            }
        }
    }

    public void runGame() {
        while (true) {
            printingBoard();

            if (Symbol == 'X') {
                setPlayerMove(player1, playerName);
            } else {
                setComputerMove(player2);
            }

            if (checkWinning(player1)) {
                printingBoard();
                System.out.println("Computer wins!");
                grade = listPlayer2Move.size();
                lb.loadLeaderboard(2);
                lb.addUserRecord("Computer ReverseTTT", lb.reversedTTTScore(grade));
                lb.saveLeaderboard(2);
                lb.displayLeaderboard(2);
                break;
            } else if (checkWinning(player2)) {
                printingBoard();
                System.out.println(playerName + " wins!");
                grade = listPlayer1Move.size() + undoCount;
                lb.loadLeaderboard(2);
                lb.addUserRecord(playerName, lb.reversedTTTScore(grade));
                lb.saveLeaderboard(2);
                lb.displayLeaderboard(2);
                break;
            } else if (checkDraw()) {
                printingBoard();
                System.out.println("It's a tie!!!");
                break;
            }
            togglingPlayer();
        }
    }

    public void setPlayerMove(char symbol, String playerName) {
        System.out.println(playerName + "'s( player " + symbol + ")" + " turn:");
        int position = getPlayerMove();
        if (position == -2) {
            undoMove();
            //Recursion: Prompt user to make next move after undo
            setPlayerMove(symbol, playerName);
        } else if (position == -1) { //End the game
            System.out.println(playerName + " exits the game.");
            System.exit(0);
        } else {
            makeMove(position, symbol);
            if (playerName.equals(playerName)) {
                listPlayer1Move.add(position);
            }
        }
    }

    public void undoMove() {
        System.out.println("\n-=≡(((--- Undo Previous move!!! ---)))≡=-");
        int temp1 = listPlayer1Move.getLast();
        int temp2 = listPlayer2Move.getLast();
        listPlayer1Move.removeLast();
        listPlayer2Move.removeLast();
        makeMove(temp1, ' ');
        makeMove(temp2, ' ');
        printingBoard();
    }

    public int getPlayerMove() {
        String input;
        while (true) {
            System.out.print("Enter position (1-" + (boardSize) + ") or 'exit' to quit or 'undo' move: ");
            input = scan.next();

            if (validMove(input)) { //User make a move
                int position = Integer.parseInt(input);
                if (validMove(position - 1)) {
                    return (position - 1);
                } else {
                    System.out.println("The position is occupied!");
                }
            } else if (input.equals("exit")) { //User exit the game, can save the game if want to
                System.out.print("Before quiting, do you want to save the game? (yes/no): ");
                input = scan.next();
                while (true) {
                    if (input.equals("yes")) {
                        saveGame();
                        break;
                    } else if (input.equals("no")) {
                        break;
                    }
                    System.out.print("Invalid input! Please enter (yes/no): ");
                    input = scan.next();
                }
                return -1;
            } else if (input.equals("undo")) {
                undoCount++;
                if (undoCount <= 1) {
                    return -2;
                } else {
                    System.out.println("(╬≖_≖) Sorry, you can only undo once in a game!!!");
                    printingBoard();
                }
            } else {
                System.out.println("Invalid input! Please enter a valid position or 'exit' to quit or 'undo' move: ");
            }
        }
    }

    public boolean validMove(String input) { //Check if the position of board is filled
        try {
            int index = Integer.parseInt(input);
            return (index > 0 && index < boardSize + 1);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validMove(int index) {
        return (index >= 0 && index < boardSize && board[index] == ' ');
    }

    public void setComputerMove(char symbol) {
        System.out.println("Computer's" + "(player " + symbol + ")" + " turn:");
        int index;
        switch (compDiffLvl) {
            case 1 -> {
                index = makeEasyComputerMove();
                makeMove(index, symbol);
                listPlayer2Move.add(index);
            }
            case 2 -> {
                index = makeMediumComputerMove();
                makeMove(index, symbol);
                listPlayer2Move.add(index);
            }
            case 3 -> {
                index = makeHardcomputerMove();
                makeMove(index, symbol);
                listPlayer2Move.add(index);
            }
        }
    }

    public int makeEasyComputerMove() {
        while (true) {
            int index = rand.nextInt(boardSize);
            if (board[index] == ' ') {
                return index;
            }
        }
    }

    public int makeMediumComputerMove() {
        for (int i = 0; i < boardSize; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                if (!checkWinning('O')) {
                    return i;
                } else {
                    board[i] = ' ';
                }
            }
        }
        // If neither player can win, make a random move
        return makeEasyComputerMove();
    }

    public int makeHardcomputerMove() {
        // Make a move using minimax algorithm
        int[] move = minimax(0, 'O');
        return move[0];
    }

    public int[] minimax(int depth, char player) {
        int bestIndex = -1;
        int bestScore = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if (checkWinning('X')) {
            return new int[]{-1, 10};
        } else if (checkWinning('O')) {
            return new int[]{-1, -10};
        } else if (checkDraw()) {
            return new int[]{-1, 0};
        }

        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = player;
                int[] result = minimax(depth + 1, (player == 'O') ? 'X' : 'O');
                int score = result[1];

                if ((player == 'O' && score > bestScore) || (player == 'X' && score < bestScore)) {
                    bestScore = score;
                    bestIndex = i;
                }

                board[i] = ' ';
            }
        }

        return new int[]{bestIndex, bestScore};
    }

    public void makeMove(int position, char symbol) {
        board[position] = symbol;
    }

    public void togglingPlayer() { //Swap the symbol of both players
        if (Symbol == 'X') {
            Symbol = 'O';
        } else {
            Symbol = 'X';
        }
    }

    public boolean checkWinning(char symbol) {
        int i = 0;
        int j = 0;
        for (int index = 0; index < 3; index++) {
            if (board[i] == symbol && board[i + 1] == symbol && board[i + 2] == symbol) {
                return true;
            }
            i += 3;
        }

        for (int index = 0; index < 3; index++) {
            if (board[j] == symbol && board[j + 3] == symbol && board[j + 6] == symbol) {
                return true;
            }
            j += 1;
        }

        if (board[0] == symbol && board[4] == symbol && board[8] == symbol) {
            return true;
        } else if (board[2] == symbol && board[4] == symbol && board[6] == symbol) {
            return true;
        }
        return false;
    }

    public boolean checkDraw() {
        for (int index = 0; index < boardSize; index++) {
            if (board[index] == ' ') {
                return false;

            }
        }
        return true;
    }

    public void mainMenu() {
        System.out.println("================================================\n|    Welcome to Reversed Tic Tac Toe (PvE)!    |\n================================================");
        System.out.print("1. Start a New Game!\n2. Load Game\n3. Exit Game\nSelect by entering (1-3): ");
        while (true) {
            menuChoice = scan.next();
            if (menuChoice.equals("1")) {
                getPlayerName();
                chooseDifficulty();
                runGame();
                break;
            } else if (menuChoice.equals("2")) {
                loadGame();
                break;
            } else if (menuChoice.equals("3")) {
                System.out.println("Exiting Game... QAQ");
                break;
            } else {
                System.out.print("Invalid Input! Please enter (1 - 3): ");
            }
        }
    }

    public void saveGame() {
        System.out.print("Enter the file name to save the game: ");
        String fileName = scan.next() + ".txt";

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            //Difficulty:
            writer.write(String.valueOf(compDiffLvl));
            writer.newLine();
            //Player 1:
            writer.write(playerName);
            writer.newLine();

            //Moves Player 1:
            if (listPlayer1Move.isEmpty()) {
                writer.write("0");
            } else {
                for (int move : listPlayer1Move) {
                    writer.write(move + " ");
                }
            }
            writer.newLine();

            //Moves Player 2:
            if (listPlayer2Move.isEmpty()) {
                writer.write("No moves");
            } else {
                for (int move : listPlayer2Move) {
                    writer.write(move + " ");
                }
            }
            writer.newLine();

            //Determine this game use undo once or not
            writer.write(String.valueOf(undoCount));
            writer.newLine();

            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error occurred while saving the game: " + e.getMessage());
        }
    }

    public void loadGame() {
        System.out.print("Enter the file name to load the game: ");
        String fileName = scan.next() + ".txt";

        try ( BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineCount = 1;
            while ((line = reader.readLine()) != null) {
                uploadList.add(line);
                lineCount++;
            }
            reader.close();
            System.out.println("Game Loaded Successfully...");

            // Set the computer difficulty
            int difficulty = Integer.parseInt(uploadList.get(0));
            compDiffLvl = difficulty;

            // Set the player name
            playerName = uploadList.get(1);

            // Restore player 1's moves
            String player1Moves = uploadList.get(2);
            if (!player1Moves.equals("No moves")) {
                String[] moves = player1Moves.split(" ");
                for (String move : moves) {
                    int position = Integer.parseInt(move);
                    listPlayer1Move.add(position);
                    makeMove(position, player1);
                }
            }

            // Restore player 2's moves
            String player2Moves = uploadList.get(3);
            if (!player2Moves.equals("No moves")) {
                String[] moves = player2Moves.split(" ");
                for (String move : moves) {
                    int position = Integer.parseInt(move);
                    listPlayer2Move.add(position);
                    makeMove(position, player2);
                }
            }

            //Restore undoCount
            undoCount = Integer.parseInt(uploadList.get(4));

            // Continue the game from the loaded state
            runGame();

        } catch (IOException e) {
            while(true) {
                System.out.print("Error occurred while loading the game! Load again? (yes/no): ");
                String yesNo = scan.next();
                if (yesNo.equals("yes")) {
                    loadGame();
                    break;
                } else if (yesNo.equals("no")) {
                    System.exit(0);
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }

    public void playGame() {
        mainMenu();
        System.out.println("Thanks for playing!");
    }
}
