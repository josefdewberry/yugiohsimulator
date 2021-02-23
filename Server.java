package yugioh;

import java.io.*;
import java.net.*;

import yugioh.Player;
import yugioh.RPS;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket listener = new ServerSocket(9090);

        System.out.println("Waiting for player to connect...");
        Socket s = listener.accept();
        System.out.println("Player 1 connected!");
        System.out.println("Waiting for player to connect...");
        Socket s2 = listener.accept();
        System.out.println("Player 2 connected!");

        Player p1 = new Player(s);
        Player p2 = new Player(s2);
        
        Player.buildDecks(p1, p2);

        int winner = RPS.play(p1, p2);

        if (winner == 1) Game.start(p1, p2);
        else if (winner == 2) Game.start(p2, p1);

        System.out.println("Something went wrong!");

        listener.close();
    }
}