package ReverseTTT;

import Leaderboard.Leaderboard;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class ReversedPvP {
    private Leaderboard lb = new Leaderboard();
    private static final int boardSize = 9;
    private char[] board;
    private char player1;
    private char player2;
    private char Symbol;
    private Scanner scan;
    private Random rand;
    private LinkedList<Integer> listPlayer1Move;
    private LinkedList<Integer> listPlayer2Move;
    private LinkedList<String> uploadList;
    private String player1Name;
    private String player2Name;
    private int undoCountp1;
    private int undoCountp2;
    protected int grade;
    public String menuChoice;

    public ReversedPvP() {
        board = new char[boardSize];
        player1 = 'X';
        player2 = 'O';
        scan = new Scanner(System.in);
        rand = new Random();
        listPlayer1Move = new LinkedList<>();
        listPlayer2Move = new LinkedList<>();
        uploadList = new LinkedList<>();
        undoCountp1 = 0;
        undoCountp2 = 0;
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

    public void getPlayer1Name() {
        System.out.print("Enter Player1's name: ");
        player1Name = scan.next();
    }

    public void getPlayer2Name() {
        System.out.print("Enter Player2's name: ");
        player2Name = scan.next();
    }

    public void runGame() {
        while (true) {
            printingBoard();

            if (Symbol == 'X') {
                setPlayerMove(player1, player1Name);
            } else {
                setPlayerMove(player2, player2Name);
            }

            if (checkWinning(player1)) {
                printingBoard();
                System.out.println(player2Name + " wins!");
                grade = listPlayer2Move.size() + undoCountp2;
                lb.loadLeaderboard(5);
                lb.addUserRecord(player2Name, lb.reversedTTTScore(grade));
                lb.saveLeaderboard(5);
                lb.displayLeaderboard(5);
                break;
            } else if (checkWinning(player2)) {
                printingBoard();
                System.out.println(player1Name + " wins!");
                grade = listPlayer1Move.size() + undoCountp1;
                lb.loadLeaderboard(5);
                lb.addUserRecord(player1Name, lb.reversedTTTScore(grade));
                lb.saveLeaderboard(5);
                lb.displayLeaderboard(5);
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
        System.out.println(playerName + "('s player " + symbol + ")" + " turn:");
        int position = getPlayerMove(playerName);
        if (position == -2) {
            undoMove();
            //Recursion: Prompt user to make next move after undo
            setPlayerMove(symbol, playerName);
        } else if (position == -1) { //End the game
            System.out.println(playerName + " exits the game.");
            System.exit(0);
        } else {
            makeMove(position, symbol);
            if (playerName.equals(player1Name)) {
                listPlayer1Move.add(position);
            } else {
                listPlayer2Move.add(position);
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

    public int getPlayerMove(String playerName) {
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
                if (playerName.equals(player1Name)) {
                    undoCountp1++;
                    if (undoCountp1 <= 1) {
                        return -2;
                    } else {
                        System.out.println("(╬≖_≖) Sorry, you can only undo once in a game!!!");
                        printingBoard();
                    }
                } else {
                    undoCountp2++;
                    if (undoCountp2 <= 1) {
                        return -2;
                    } else {
                        System.out.println("(╬≖_≖) Sorry, you can only undo once in a game!!!");
                        printingBoard();
                    }
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

    public void makeMove(int position, char symbol) {
        board[position] = symbol;
    }

    public void togglingPlayer() {
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
        System.out.println("================================================\n|    Welcome to Reversed Tic Tac Toe (PvP)!    |\n================================================");
        System.out.print("1. Start a New Game!\n2. Load Game\n3. Exit Game\nSelect by entering (1-3): ");
        while (true) {
            menuChoice = scan.next();
            if (menuChoice.equals("1")) {
                getPlayer1Name();
                getPlayer2Name();
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

            //Player 1:
            writer.write(player1Name);
            writer.newLine();

            //Player 2:
            writer.write(player2Name);
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
            writer.write(String.valueOf(undoCountp1));
            writer.newLine();

            writer.write(String.valueOf(undoCountp2));
            writer.newLine();

            writer.write(String.valueOf(Symbol));
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

            // Set the player1 name
            player1Name = uploadList.get(0);

            // Set the player1 name
            player2Name = uploadList.get(1);

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
            undoCountp1 = Integer.parseInt(uploadList.get(4));
            undoCountp2 = Integer.parseInt(uploadList.get(5));

            //restore turns
            Symbol = uploadList.get(6).charAt(0);

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
