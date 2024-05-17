package ReverseTTT;

import java.util.Scanner;

public class MainRunReversed {

    Scanner scan = new Scanner(System.in);

    public void goBack() {
        while (true) {
            System.out.print("Do you want to play another type of Reversed Tic Tac Toe game? enter: (yes/no): ");
            String input = scan.nextLine();
            if (input.equals("yes")) {
                chooseGame();
                break;
            } else if (input.equals("no")) {
                break;
            } else {
                System.out.println("Invalid input please enter yes/no only");
            }

        }
    }

    public void chooseGame() {

        ReversedPvE pve = new ReversedPvE();
        ReversedPvP pvp = new ReversedPvP();
        ReversedEvE eve = new ReversedEvE();

        System.out.println("╔═══════════════════════════════════════╗\n║    Welcome to Reversed Tic Tac Toe!   ║\n╚═══════════════════════════════════════╝");
        System.out.print("Choose mode to play\n1. Player vs Engine(PvE)\n2. Player vs Player(PvP)\n3. Engine vs Engine(EvE)\nYour choice: ");
        String mode = scan.nextLine();
        label:
        while (true) {
            switch (mode) {
                case "1":
                    System.out.println("");
                    pve.playGame();
                    if (pve.menuChoice.equals("3")) {
                        goBack();
                    }
                    break label;
                case "2":
                    System.out.println("");
                    pvp.playGame();
                    if (pvp.menuChoice.equals("3")) {
                        goBack();
                    }
                    break label;
                case "3":
                    System.out.println("");
                    eve.playGame();
                    if (eve.menuChoice.equals("2")) {
                        goBack();
                    }
                    break label;
                default:
                    System.out.print("Invalid input! Please enter (1-3): ");
                    mode = scan.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        MainRunReversed run = new MainRunReversed();
        run.chooseGame();
    }

}
