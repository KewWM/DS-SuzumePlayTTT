package NormalTTT;

import java.util.Scanner;

public class MainRunTTT
{
    protected int mode;
    protected Scanner scanner;

    public MainRunTTT()
    {
        mode = 1;
        scanner = new Scanner(System.in);
    }

    public void mode()
    {
        NormalTTT_PvE pve = new NormalTTT_PvE();
        NormalTTT_PvP pvp = new NormalTTT_PvP();
        NormalTTT_EvE eve = new NormalTTT_EvE();
        System.out.println("╔══════════════════════════════╗\n║    Welcome to Tic Tac Toe!   ║\n╚══════════════════════════════╝");
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
        MainRunTTT run = new MainRunTTT();
        run.mode();
    }
}
