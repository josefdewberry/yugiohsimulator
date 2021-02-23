package yugioh;

import java.io.File;
import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import yugioh.Action;
import yugioh.Card.*;
import yugioh.CardType;
import yugioh.Field;
import yugioh.PlayerBuilder;
import yugioh.Player;


public class Game {

    static boolean firstTurn = true;

    Player p1;
    Player p2;

    public static int first(Player p1, Player p2) throws IOException {

        p2.noResponse("Waiting on opponent...");
        p1.needResponse("Do you want to go first or second?");
        String first = p1.in.readLine();
        while (!first.equalsIgnoreCase("first") && !first.equalsIgnoreCase("second")) {
            p1.noResponse("Invalid input.");
            p1.needResponse("Do you want to go first or second?");
            first = p1.in.readLine();
        }

        if (first.equalsIgnoreCase("first")) {
            return 1;
        } else if (first.equalsIgnoreCase("second")) {
            return 2;
        }

        return 0;
    }

    public static void start(Player p1, Player p2) throws IOException, InterruptedException {
        p1.noResponse("Shuffling deck...");
        p2.noResponse("Shuffling deck...");
        p1.shuffle();
        p2.shuffle();
        Thread.sleep(1000);
        p1.noResponse("Deck shuffled.");
        p2.noResponse("Deck shuffled.");
        Thread.sleep(1000);
        p1.noResponse("");
        p2.noResponse("");
        p1.noResponse("Drawing hand...");
        p2.noResponse("Drawing hand...");
        Thread.sleep(1000);
        for (int i = 0; i < 5; i++) {
            p1.draw();
            p2.draw();
            p1.noResponse(p1.hand.get(i).getName());
            p2.noResponse(p2.hand.get(i).getName());
            Thread.sleep(500);
        }

        drawPhase(p1, p2);
    }

    public static void drawPhase(Player p1, Player p2) throws IOException, InterruptedException {        
        p1.noResponse("");
        p2.noResponse("");
        p1.noResponse("Draw Phase");
        p2.noResponse("Opponents Draw Phase");
        Thread.sleep(750);
        if (firstTurn) {
        } else {
            p1.draw();
        }
        standbyPhase(p1, p2);
    }

    public static void standbyPhase(Player p1, Player p2) throws IOException, InterruptedException {
        p1.noResponse("");
        p2.noResponse("");
        p1.noResponse("Standby Phase");
        p2.noResponse("Opponents Standby Phase");
        Thread.sleep(750);

        mainPhase(p1, p2, 1);
    }

    public static void mainPhase(Player p1, Player p2, int phase) throws IOException, InterruptedException {
        p1.noResponse("");
        p2.noResponse("");
        p1.noResponse("Main Phase " + phase);
        p2.noResponse("Opponents Main Phase " + phase);

        p1.displayHand();
        
        Action action = Action.TURNSTART;

        do {

            if (action == null) {
                p1.noResponse("Invalid input.");

            }
            p1.noResponse("");
            p1.needResponse("What would you like to do?");
            action = Action.findMatch(p1.in.readLine());
            
            if (action == Action.NORMALSUMMON) {
                p1.normalSummon(p2);
            } else if (action == Action.CHECKFIELD) {
                p1.checkField(p2);
            } else if (action == Action.CHECKHAND) {
                p1.displayHand();
            } else if (action == Action.CHECKGY) {
                p1.checkGraveyard();
            } else if (action == Action.CHECKOPPGY) {
                p1.checkGraveyard(p2);
            } else if (action == Action.TRIBUTESUMMON) {
                p1.tributeSummon(p2);
            } else if (action == Action.BATTLEPHASE && phase == 1 && !firstTurn) {
                battlePhase(p1, p2);
            } else if (action == Action.SWITCHPOSITION) {
                p1.switchPosition(p2);
            }
        } while (action == null || action != Action.ENDTURN);

        endPhase(p1, p2);
    }

    public static void battlePhase(Player p1, Player p2) throws IOException, InterruptedException {
        p1.noResponse("");
        p2.noResponse("");
        p1.noResponse("Battle Phase");
        p2.noResponse("Opponent's Battle Phase");

        Action action = Action.TURNSTART;

        do {

            if (action == null) {
                p1.noResponse("Invalid input.");

            }
            p1.noResponse("");
            p1.needResponse("What would you like to do?");
            action = Action.findMatch(p1.in.readLine());
            
            if (action == Action.ATTACK) {
                p1.attack(p2);
            }
            if (action == Action.MAINPHASE) {
                mainPhase(p1, p2, 2);
            }
        } while (action == null || action != Action.ENDTURN);

        endPhase(p1, p2);
    }

    public static void endPhase(Player p1, Player p2) throws IOException, InterruptedException {
        p1.noResponse("");
        p2.noResponse("");
        p1.noResponse("End Phase");
        p2.noResponse("Opponent's End Phase");

        if (p1.hand.size() > 6) {
            while (p1.hand.size() > 6) {
                p1.noResponse("");
                p1.noResponse("Choose a card to discard.");
                p1.noResponse("");
                p1.displayHand();
                p1.needResponse("");
                while (!p1.discard(p1.in.readLine())) {
                    p1.noResponse("Card not found.");
                    p1.noResponse("");
                    p1.noResponse("Choose a card to discard.");
                    p1.displayHand();
                    p1.needResponse("");
                }
            }
        } else {
            Thread.sleep(750);
        }

        for (int i = 0; i < p1.monsterZones.size(); i++) {
            p1.monsterZones.get(i).switched = false;
            p1.monsterZones.get(i).attacked = false;
        }
        for (int i = 0; i < p2.monsterZones.size(); i++) {
            p2.monsterZones.get(i).attacked = false;
        }
        p1.normalSummoned = false;
        p2.normalSummoned = false;
        firstTurn = false;
        drawPhase(p2, p1);
    }

    public static void checkForResponse(Player p1, Player p2) throws IOException {
        if (p1.lifePoints <= 0 && p2.lifePoints <= 0) {
            p1.noResponse("It's a draw!");
            p2.noResponse("It's a draw!");
            p1.noResponse("quit program");
            p1.noResponse("quit program");
        } else if (p1.lifePoints <= 0) {
            p1.noResponse("You lose!");
            p2.noResponse("You win!");
            p1.noResponse("quit program");
            p1.noResponse("quit program");
        } else if (p2.lifePoints <= 0) {
            p1.noResponse("You win!");
            p2.noResponse("You lose!");
            p1.noResponse("quit program");
            p1.noResponse("quit program");
        }
    }
}