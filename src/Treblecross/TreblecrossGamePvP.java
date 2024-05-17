package Treblecross;

import Leaderboard.Leaderboard;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class TreblecrossGamePvP extends Leaderboard {
    private Leaderboard lb = new Leaderboard();
    protected static final int BOARD_SIZE = 11;
    protected char[] board;
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
    protected int grade = 0;
    public TreblecrossGamePvP() {
        board = new char[BOARD_SIZE];
        player1Symbol = 'X';
        player2Symbol = 'O';
        scanner = new Scanner(System.in);
        computerDifficulty = 1;
        listPlayer1 = new LinkedList<>();
        listPlayer2 = new LinkedList<>();
        loadingList = new LinkedList<>();
        undoCount1 = 0;
        undoCount2 = 0;
        grade = 0;

        for (int i = 0; i < BOARD_SIZE; i++)
            board[i] = ' ';
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
    protected void menu()
    {
        System.out.println("╔════════════════════════════════════╗\n║    Welcome to Treblecross(PvP)!    ║\n╚════════════════════════════════════╝");
        System.out.print("1. Start a New Game!\n2. Load Game\n3. Exit Game\nSelect by entering (1-3): ");
        String menuChoice;
        while (true)
        {
            menuChoice = scanner.nextLine();
            if (menuChoice.equals("1"))
            {
                getPlayerName();
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

            if (player1Symbol == 'X') {
                makePlayerMove(player1Symbol, player1Name);
            }
            else{
                makePlayerMove(player2Symbol, player2Name);
            }


            if (isWinningMove(player1Symbol))
            {
                printBoard();
                System.out.println(player1Name + " wins!");
                grade = listPlayer1.size() + undoCount1;
                lb.loadLeaderboard(6);
                lb.addUserRecord(player1Name, lb.treblecrossScore(grade));
                lb.saveLeaderboard(6);
                lb.displayLeaderboard(6);
                break;
            }
            else if (isWinningMove(player2Symbol))
            {
                printBoard();
                System.out.println(player2Name + " wins!");
                grade = listPlayer1.size() + undoCount2;
                lb.loadLeaderboard(6);
                lb.addUserRecord(player2Name, lb.treblecrossScore(grade));
                lb.saveLeaderboard(6);
                lb.displayLeaderboard(6);
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
            makeMove(position, symbol);
            if (playerName.equals(player1Name))
                listPlayer1.add(position);
            else if (playerName.equals(player2Name))
                listPlayer2.add(position);
        }
    }

    protected void undo(String player)
    {
        if (player.equals(player1Name))
        {
            int temp1, temp2;
            temp1 = listPlayer1.getLast();
            temp2 = listPlayer2.getLast();
            listPlayer1.removeLast();
            listPlayer2.removeLast();
            makeMove(temp1, ' ');
            makeMove(temp2, ' ');
        }
        else if (player.equals(player2Name))
        {
            int temp1, temp2;
            temp2 = listPlayer2.getLast();
            temp1 = listPlayer1.getLast();
            listPlayer2.removeLast();
            listPlayer1.removeLast();
            makeMove(temp2, ' ');
            makeMove(temp1, ' ');
        }
        System.out.println("\n-=≡Σ(((⊃ﾟ∀ﾟ)つ Undo Previous move!!! -=≡Σ(((⊃ﾟ∀ﾟ)つ");
        printBoard();
    }

    protected int getPlayerMove(String name)
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
                    if (name.equals(player1Name) && !listPlayer1.isEmpty())
                    {
                        if(undoCount1 < 1) {
                            undoCount1++;
                            return -2;
                        }
                        else
                        {
                            System.out.printf("(╬≖_≖) Sorry %s, you can only undo once in a game!!!\n", player1Name);
                            printBoard();
                        }
                    }
                    else if (name.equals(player2Name) && !listPlayer2.isEmpty())
                    {

                        if(undoCount2 < 1){
                            undoCount2++;
                            return -2;
                        }
                        else
                        {
                            System.out.printf("(╬≖_≖) Sorry %s, you can only undo once in a game!!!\n", player2Name);
                            printBoard();
                        }
                    }

                else {
                    System.out.println("(☞◣д◢)☞ You can't undo in the first round!");
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

            //Determine palyer1 use undo once or not
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
                    makeMove(position, player1Symbol);
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
                    makeMove(position, player1Symbol);
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
            String yesNo = scanner.nextLine();
            if (yesNo.equals("yes"))
                loadGame();
            else
                System.exit(0);
        }
    }
}
