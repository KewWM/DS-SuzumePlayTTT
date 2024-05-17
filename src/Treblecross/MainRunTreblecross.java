package Treblecross;

import java.util.Scanner;

public class MainRunTreblecross
{
    protected int mode;
    protected Scanner scanner;

    public MainRunTreblecross()
    {
        mode = 1;
        scanner = new Scanner(System.in);
    }

    public void mode()
    {
        TreblecrossGamePvE pve = new TreblecrossGamePvE();
        TreblecrossGamePvP pvp = new TreblecrossGamePvP();
        TreblecrossGameEvE eve = new TreblecrossGameEvE();
        System.out.println("╔══════════════════════════════╗\n║    Welcome to Treblecross!   ║\n╚══════════════════════════════╝");
        System.out.print("Choose mode to play\n1. Player vs Engine(PvE)\n2. Player vs Player(PvP)\n3. Engine vs Engine(EvE)\nYour choice: ");
        label:
        while(true)
        {
            String mode = scanner.nextLine();
            switch (mode) {
                case "1":
                    pve.play();
                    break label;
                case "2":
                    pvp.play();
                    break label;
                case "3":
                    eve.play();
                    break label;
                default:
                    System.out.print("Invalid input! Please enter (1-3): ");
            }
        }
    }

    public static void main(String[] args) {
        MainRunTreblecross gm = new MainRunTreblecross();
        gm.mode();
    }
}
