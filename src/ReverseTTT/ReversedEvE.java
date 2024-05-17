package ReverseTTT;

import Leaderboard.Leaderboard;
import java.util.Random;
import java.util.Scanner;

public class ReversedEvE {
    private static final int boardSize = 9;
    private char[] board;
    private char player1;
    private char player2;
    private char Symbol;
    private Random rand;
    private Scanner scan;
    private int compDiffLvlP1;
    private int compDiffLvlP2;
    private String player1Name;
    private String player2Name;
    public String menuChoice;

    public ReversedEvE() {
        board = new char[boardSize];
        player1 = 'X';
        player2 = 'O';
        rand = new Random();
        scan = new Scanner(System.in);
        compDiffLvlP1 = 1;
        compDiffLvlP2 = 1;
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

    public int chooseDifficulty(String playerName) {

        if (playerName.equals(player1Name)) {
            int random = rand.nextInt(9);
            if (random <= 2) {
                return compDiffLvlP1 = 1;
            } else if (random > 2 && random <= 5) {
                return compDiffLvlP1 = 2;
            } else {
                return compDiffLvlP1 = 3;
            }

        } else {
            int random = rand.nextInt(9);
            if (random <= 2) {
                return compDiffLvlP1 = 1;
            } else if (random > 2 && random <= 5) {
                return compDiffLvlP1 = 2;
            } else {
                return compDiffLvlP1 = 3;
            }
        }
    }

    public void runGame() {
        while (true) {
            printingBoard();

            if (Symbol == 'X') {
                setComputerMove(Symbol, player1Name);
            } else {
                setComputerMove(Symbol, player2Name);
            }

            if (checkWinning(player1)) {
                printingBoard();
                System.out.println(player2Name + " wins!");
                break;
            } else if (checkWinning(player2)) {
                printingBoard();
                System.out.println(player1Name + " wins!");
                break;
            } else if (checkDraw()) {
                printingBoard();
                System.out.println("It's a tie!!!");
                break;
            }
            togglingPlayer();
        }
    }

    public boolean validMove(String input) { //Check if the position of board is filled
        try {
            int index = Integer.parseInt(input);
            return (index >= 0 && index < boardSize + 1);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validMove(int index) {
        return (index >= 0 && index < boardSize && board[index] == ' ');
    }

    public void setComputerMove(char symbol, String playerName) {
        System.out.println(playerName + "'s(player " + symbol + ")" + " turn:");
        int index;
        int difficultChoosen;
        if (playerName.equals(player1Name)) {
            difficultChoosen = compDiffLvlP1;
        } else {
            difficultChoosen = compDiffLvlP2;
        }
        switch (difficultChoosen) {
            case 1 -> {
                index = makeEasyComputerMove();
                makeMove(index, symbol);

            }
            case 2 -> {
                index = makeMediumComputerMove();
                makeMove(index, symbol);
            }
            case 3 -> {
                index = makeHardcomputerMove();
                makeMove(index, symbol);
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
                board[i] = Symbol;
                if (!checkWinning(Symbol)) {
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
        int[] move = minimax(0, Symbol);
        return move[0];
    }

    public int[] minimax(int depth, char player) {

        int bestIndex = -1;
        int bestScore = 0;
        if (Symbol == 'X') {
            bestScore = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

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
        } else {
            bestScore = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

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
        }

        return new int[]{bestIndex, bestScore};
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
        System.out.println("================================================\n|    Welcome to Reversed Tic Tac Toe (EvE)!    |\n================================================");
        System.out.print("1. Start a Game!\n2. Exit Game\nSelect by entering (1-2): ");
        while (true) {
            menuChoice = scan.next();
            if (menuChoice.equals("1")) {
                getPlayer1Name();
                chooseDifficulty(player1Name);
                getPlayer2Name();
                chooseDifficulty(player2Name);
                runGame();
                break;
            } else if (menuChoice.equals("2")) {
                System.out.println("Exiting Game... QAQ");
                break;
            } else {
                System.out.print("Invalid Input! Please enter (1 - 2): ");
            }
        }
    }

    public void playGame() {
        mainMenu();
        System.out.println("Thanks for playing!");
    }
}
