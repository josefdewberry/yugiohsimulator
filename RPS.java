package yugioh;

import java.io.IOException;

import yugioh.Player;

/**
 * A simple Rock, Paper, Scissors program to determine which player gets to decide
 * who goes first.
 * 
 * @author josefdewberry
 */
public enum RPS {

    // The 3 moves in Rock, Paper, Scissors.
    ROCK,
    PAPER,
    SCISSORS;

    /**
     * Finds a match for the given string and returns the appropriate RPS enum.
     * The method will accept both the first letter of the move or the whole word.
     * 
     * @param s The user's input.
     * @return The proper RPS enum, or null if the input is invalid.
     */
    public static RPS findMatch(String s) {
        if (s.equalsIgnoreCase("r") || s.equalsIgnoreCase("rock")) return ROCK;
        else if (s.equalsIgnoreCase("p") || s.equalsIgnoreCase("paper")) return PAPER;
        else if (s.equalsIgnoreCase("s") || s.equalsIgnoreCase("scissors")) return SCISSORS;
        else return null;
    }

    /**
     * Determines who actually won the game of Rock, Paper, Scissors.
     * 
     * @param rps1 Player 1's move.
     * @param rps2 Player 2's move.
     * @return The winner as an int, or 0 if they tied.
     */
    public static int compare(RPS rps1, RPS rps2) {
        // If they both gave the same move, it's a tie.
        if (rps1 == rps2) return 0;

        // Compare both moves and return the winner.
        if (rps1 == ROCK) {
            if (rps2 == SCISSORS) return 1;
            else return 2;
        } else if (rps1 == PAPER) {
            if (rps2 == ROCK) return 1;
            else return 2;
        } else if (rps1 == SCISSORS) {
            if (rps2 == PAPER) return 1;
            else return 2;
        }

        // If we reach here then something went wrong, return 0 just in case.
        return 0;
    }

    /**
     * Given two players, plays a game of Rock, Paper, Scissors, validating their input
     * and determining a clear winner. The winner decides who goes first in the duel.
     * 
     * @param p1 Player 1
     * @param p2 Player 2
     * @return Who goes first in the duel.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public static int play(Player p1, Player p2) throws IOException {

        // Set the winner as 3 because that's an invalid winner, and so the game will
        // loop until a valid winner is found.
        int winner = 3;

        String rps1;
        String rps2;

        // Loop until a winner is found.
        do {
            // Winner of 0 means a tie.
            if (winner == 0) {
                p1.noResponse("Tie!");
                p2.noResponse("Tie!");
            }

            // Get the user's input.
            p1.needResponse("(R)ock, (P)aper, or (S)cissors?");
            p2.needResponse("(R)ock, (P)aper, or (S)cissors?");
            rps1 = p1.in.readLine();
            rps2 = p2.in.readLine();

            // Loop until both players give a valid input. Unfortunately if both player's
            // give an invalid input, player 2 won't know until player 1 gives a valid one.
            // We could fix this with a third loop that checks if both are improper at the
            // same time. Maybe I'LL DO THIS LATER!!!!!!
            while(findMatch(rps1) ==  null) {
                p1.noResponse("Inproper input.");
                rps1 = p1.needResponse("(R)ock, (P)aper, or (S)cissors?");
                rps1 = p1.in.readLine();
            }
    
            while(findMatch(rps2) == null) {
                p2.noResponse("Inproper input.");
                rps2 = p2.needResponse("(R)ock, (P)aper, or (S)cissors?");
                rps2 = p2.in.readLine();
            }

            // Find a winner (or a tie).
            winner = compare(findMatch(rps1), findMatch(rps2));

        } while(winner != 1 && winner != 2);

        // Int representing who gets to go first.
        int first;

        // Pass the winner in first because the first parameter is the one we ask who gets
        // to go first in the duel.
        if (winner == 1) {
            p1.noResponse("You win!");
            p2.noResponse("You lose.");
            first = Game.first(p1, p2);
            return first;
        } else {
            p1.noResponse("You lose.");
            p2.noResponse("You win!");
            first = Game.first(p2, p1);
            // Little math trickery, we want an int that represents who gets to go first, but
            // Game.first() returns an int relative to the winner of the RPS game. To fix this
            // we flip the result if player 2 is the winner 2 / 2 = 1, and 2 / 1 = 2.
            // Unnecessary? Yes, but it made me feel smart.
            return (2 / first);
        }
    }   
}