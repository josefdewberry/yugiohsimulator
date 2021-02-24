package yugioh;

import java.io.*;
import java.net.*;

import yugioh.Player;
import yugioh.RPS;

/**
 * Simple server program which runs through the main logic of the game, while keeping both
 * clients saved as player objects.
 * 
 * @author josefdewberry
 */
public class Server {
    /**
     * Main method which creates the client/player objects and then pipes them into the 
     * game.
     * 
     * @param args Command line arguments, none needed.
     * @exception IOException If something goes wrong passing input/output with the clients
     * @exception InterruptedException We use Thread.sleep() to make the game feel more fluid.
     */
    public static void main(String[] args) throws IOException, InterruptedException{

        // Make the ServerSocket which will accept the sockets.
        ServerSocket listener = new ServerSocket(9090);

        // The only messages the server prints are here while we're waiting for 
        // exactly two players to connect.
        System.out.println("Waiting for player to connect...");
        Socket s = listener.accept();
        Player p1 = new Player(s);
        // Let the first player to connect know the program is simply waiting.
        p1.noResponse("Waiting on opponent...");
        System.out.println("Player 1 connected!");
        System.out.println("Waiting for player to connect...");
        Socket s2 = listener.accept();
        System.out.println("Player 2 connected!");
        Player p2 = new Player(s2);
        // Close the listener to prevent memory leak.
        listener.close();
        
        // Build the decks for each player.
        Player.buildDecks(p1, p2);

        // Play a game of Rock, Paper, Scissors to determine who gets to decide who
        // goes first.
        int winner = RPS.play(p1, p2);

        // Start the game, with the first player to take their turn as the first
        // parameter in the Game.start() funcion.
        if (winner == 1) Game.start(p1, p2);
        else if (winner == 2) Game.start(p2, p1);

        // The program should end from within the game logic, so we should never reach
        // this point.
        throw new IOException("Program ended outside of game logic.");
    }
}