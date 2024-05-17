import NormalTTT.*;
import ReverseTTT.*;
import Treblecross.*;

import java.util.Scanner;

public class Launch
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MainRunTTT game1 = new MainRunTTT();
        MainRunReversed game2 = new MainRunReversed();
        MainRunTreblecross game3 = new MainRunTreblecross();
        System.out.print("Choose game (1-3) to test: ");
        int choice = sc.nextInt();
        if (choice == 1)
            game1.mode();
        else if(choice == 2)
            game2.chooseGame();
        else if (choice == 3)
            game3.mode();
    }
}