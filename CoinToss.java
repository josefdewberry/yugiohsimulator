package yugioh;

import java.net.*;
import java.io.*;
import java.util.Random;

public class CoinToss {
    
    public static int coinToss(Socket s, Socket s2) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        PrintWriter out2 = new PrintWriter(s2.getOutputStream(), true);

        out.println("Coin Toss:");
        out2.println("Coin Toss:");

        out.print("Heads or Tails?: ");
        out2.println("Player 1 is calling the coin toss.");

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        String call = in.readLine();
        call = call.toLowerCase();
        while(!call.equals("heads") || !call.equals("tails")) {
            out.println("Invalid input.");
            out.print("Heads or Tails?: ");
            call = in.readLine();
            call = call.toLowerCase();
        }

        out2.println("Player 1 called " + call + ".");

        Random rand = new Random();

        int coin = rand.nextInt(2);

        switch(coin) {
            case 0:
                out.println("Heads!");
                out2.println("Heads!");
                if (call.equals("heads")) {
                    out.println("You get to choose who goes first.");
                    out2.println("Player 1 gets to choose who goes first.");
                    return 1;
                } else {
                    out.println("Player 2 gets to choose who goes first.");
                    out2.println("You get to choose who goes first.");
                    return 2;
                }
            case 1:
                out.println("Tails!");
                out2.println("Tails!");
                if (call.equals("tails")) {
                    out.println("You get to choose who goes first.");
                    out2.println("Player 1 gets to choose who goes first.");
                    return 1;
                } else {
                    out.println("Player 2 gets to choose who goes first.");
                    out2.println("You get to choose who goes first.");
                    return 2;
                }
        }


        return 0;
    }

}
