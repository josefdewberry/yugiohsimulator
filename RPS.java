package yugioh;

import java.io.IOException;
import yugioh.Player;

public enum RPS {
    ROCK,
    PAPER,
    SCISSORS;

    public static boolean properInput(String s) {
        if (s.equalsIgnoreCase("r") || s.equalsIgnoreCase("p") ||
            s.equalsIgnoreCase("s")) return true;
        else if (s.equalsIgnoreCase("rock") || s.equalsIgnoreCase("paper") || 
                 s.equalsIgnoreCase("scissors")) return true;
        else return false;
    }

    public static RPS findMatch(String s) {
        if (s.equalsIgnoreCase("r") || s.equalsIgnoreCase("rock")) return ROCK;
        else if (s.equalsIgnoreCase("p") || s.equalsIgnoreCase("paper")) return PAPER;
        else if (s.equalsIgnoreCase("s") || s.equalsIgnoreCase("scissors")) return SCISSORS;
        else return null;
    }

    public static int compare(RPS rps1, RPS rps2) {
        if (rps1 == rps2) return 0;

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

        return 0;
    }

    public static int play(Player p1, Player p2) throws IOException {

        int winner = 3;

        String rps;
        String rps2;

        do {
            if (winner == 0) {
                p1.noResponse("Tie!");
                p2.noResponse("Tie!");
            }

            p1.needResponse("(R)ock, (P)aper, or (S)cissors?");
            p2.needResponse("(R)ock, (P)aper, or (S)cissors?");
            rps = p1.in.readLine();
            rps2 = p2.in.readLine();

            while(!properInput(rps)) {
                p1.noResponse("Inproper input.");
                p1.needResponse("(R)ock, (P)aper, or (S)cissors?");
                rps = p1.in.readLine();
            }
    
            while(!properInput(rps2)) {
                p2.noResponse("Inproper input.");
                p2.needResponse("(R)ock, (P)aper, or (S)cissors?");
                rps2 = p2.in.readLine();
            }

            winner = compare(findMatch(rps), findMatch(rps2));

        } while(winner != 1 && winner != 2);

        int first;

        if (winner == 1) {
            p1.noResponse("You win!");
            p2.noResponse("You lose.");
            first = Game.first(p1, p2);
            return first;
        } else {
            p1.noResponse("You lose.");
            p2.noResponse("You win!");
            first = Game.first(p2, p1);
            return (2 / first);
        }
    }
    
}