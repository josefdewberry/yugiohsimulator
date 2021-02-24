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

/**
 * The game logic of the yugioh game, separated into the 6 phases of each turn. After one player's
 * turn, the game loops back to the first phase, but switches the players in the parameters,
 * ensuring a loop until a win condition has been met.
 * 
 * @author josefdewberry
 */
public class Game {

    // A flag for if its the first turn. During the first turn the playing player cannot draw
    // during their draw phase nor enter the battle phase.
    static boolean firstTurn = true;

    /**
     * Ran before the game to determine who gets to go first. The winner of Rock, Paper,
     * Scissors gets to decide.
     * 
     * @param p1 The winner of Rock, Paper, Scissors.
     * @param p2 The loser of Rock, Paper, Scissors.
     * @return Who will go first. If the winner was player 2 then the result needs to be
     * flip-flopped, which RPS.play() does when it receives the return. After the duel
     * starts the difference between which player/client connected to the server first
     * doesn't matter. 
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public static int first(Player p1, Player p2) throws IOException {

        // Get the winner's input.
        p2.noResponse("Waiting on opponent...");
        p1.needResponse("Do you want to go first or second?");
        String first = p1.in.readLine();
        // Loop until the winner gives us a valid input.
        while (!first.equalsIgnoreCase("first") && !first.equalsIgnoreCase("second")) {
            p1.noResponse("Invalid input.");
            p1.needResponse("Do you want to go first or second?");
            first = p1.in.readLine();
        }

        // Return who goes first.
        if (first.equalsIgnoreCase("first")) {
            return 1;
        } else if (first.equalsIgnoreCase("second")) {
            return 2;
        }

        // The method shouldn't reach here.
        return 0;
    }

    /**
     * Sets up the duel.
     * 
     * @param p1 Player 1.
     * @param p2 Player 2. 
     * @throws IOException If something goes wrong passing input/output with the clients.
     * @throws InterruptedException We use Thread.sleep() to make the duel feel more fluid.
     */
    public static void start(Player p1, Player p2) throws IOException, InterruptedException {
        // Shuffle both players decks.
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
        // Draw both initial hands, and also show the players the hands as they draw them.
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
        p1.noResponse("");
        p2.noResponse("");

        // Begin the game with the first player's draw phase.
        drawPhase(p1, p2);
    }

    /**
     * Play out the draw phase for player 1.
     * 
     * @param p1 Player 1.
     * @param p2 Player 2.
     * @throws IOException If something goes wrong passing input/output with the clients.
     * @throws InterruptedException We use Thread.sleep() to make the duel feel more fluid.
     */
    public static void drawPhase(Player p1, Player p2) throws IOException, InterruptedException {        

        // Notify the players of the phase.
        p1.noResponse("Draw Phase");
        p2.noResponse("Opponents Draw Phase");
        Thread.sleep(750);
        // The draw phase does not draw on the first turn of the game, giving the second
        // player a 1 card advantage.
        if (firstTurn) {
        } else {
            if(!p1.draw()) {
                win(p2, p1);
            }
        }
        p1.noResponse("");
        p2.noResponse("");

        // Move to the standby phase.
        standbyPhase(p1, p2);
    }

    /**
     * Plays out the standby phase for player 1, in which certain card effects take place.
     * 
     * @param p1 Player 1.
     * @param p2 Player 2. 
     * @throws IOException If something goes wrong passing input/output with the clients.
     * @throws InterruptedException We use Thread.sleep() to make the duel feel more fluid.
     */
    public static void standbyPhase(Player p1, Player p2) throws IOException, InterruptedException {
        // Notify the players of the phase.
        p1.noResponse("Standby Phase");
        p2.noResponse("Opponents Standby Phase");
        Thread.sleep(750);
        p1.noResponse("");
        p2.noResponse("");

        // Then move on to main phase 1.
        mainPhase(p1, p2, 1);
    }

    /**
     * Runs through the turn player's main phase, where they can activate cards and 
     * summon monsters.
     * 
     * @param p1 Player 1.
     * @param p2 Player 2.
     * @param phase Which main phase it currently is (1 or 2).
     * @throws IOException If something goes wrong passing input/output with the clients.
     * @throws InterruptedException We use Thread.sleep() to make the duel feel more fluid.
     */
    public static void mainPhase(Player p1, Player p2, int phase) throws IOException, InterruptedException {
        // Notify the player of the phase.
        p1.noResponse("Main Phase " + phase);
        p2.noResponse("Opponents Main Phase " + phase);
        p1.noResponse("");
        p2.noResponse("");
        
        // Set a place holder action.
        Action action = Action.TURNSTART;

        // Display the turn player's hand so they know their possibilities.
        p1.displayHand();

        // The player can do certain actions indefinitely until they decide to end their 
        // main phase.
        do {

            // Get the player's input.
            p1.needResponse("What would you like to do?");
            action = Action.findMatch(p1.in.readLine());
            p1.noResponse("");

            // This loop was earlier in the method, but it would save time having to 
            // check over every single available action if we just check immediately
            // if the user gave us an invalid input.
            while (action == null) {
                p1.noResponse("Invalid input.");
                p1.noResponse("");
                p1.needResponse("What would you like to do?");
                action = Action.findMatch(p1.in.readLine());
                p1.noResponse("");
            }
            
            // A player may normal summon, provided they haven't already that turn.
            if (action == Action.NORMALSUMMON) {
                p1.normalSummon(p2);
            // A player can check the field indefinitely.
            } else if (action == Action.CHECKFIELD) {
                p1.checkField(p2);
            // A player can check their hand indefinitely. This is blank because
            // when this while loop loops, the hand is shown by default.
            } else if (action == Action.CHECKHAND) {
                p1.displayHand();
            // A player can check their graveyard indefinitely.
            } else if (action == Action.CHECKGY) {
                p1.checkGraveyard();
            // A player can check their opponent's graveyard indefinitely.
            } else if (action == Action.CHECKOPPGY) {
                p1.checkGraveyard(p2);
            // A player can tribute summon, provided they have the appropriate tribute
            // material as well as not having normal summoned that turn.
            } else if (action == Action.TRIBUTESUMMON) {
                p1.tributeSummon(p2);
            // A player can enter their battle phase, provided it is main phase 1, and not
            // the first turn of the duel.
            } else if (action == Action.BATTLEPHASE) {
                if (firstTurn) {
                    p1.noResponse("You can't enter the Battle Phase on the first turn.");
                    p1.noResponse("");
                } else if (phase == 2) {
                    p1.noResponse("You can't enter your Battle Phase during Main Phase 2");
                    p1.noResponse("");
                } else {
                    battlePhase(p1, p2);
                }
            // A player can switch the positions of one of their monsters, provided they weren't
            // summoned this turn and haven't switched their position already this turn.
            } else if (action == Action.SWITCHPOSITION) {
                p1.switchPosition(p2);
            }
        } while (action == null || action != Action.ENDTURN);

        // Go to the end phase.
        endPhase(p1, p2);
    }

    /**
     * Play out the turn player's battle phase, in which they can attack using their monsters.
     * 
     * @param p1 Player 1.
     * @param p2 Player 2.
     * @throws IOException If something goes wrong passing input/output with the clients.
     * @throws InterruptedException We use Thread.sleep() to make the duel seem more fluid.
     */
    public static void battlePhase(Player p1, Player p2) throws IOException, InterruptedException {

        // Notify the player's of the phase.
        p1.noResponse("Battle Phase");
        p2.noResponse("Opponent's Battle Phase");

        // Set up a loop very similar to the main phase, only with much more limited options.
        Action action = Action.TURNSTART;

        do {

            // Get the player's input.
            p1.needResponse("What would you like to do?");
            action = Action.findMatch(p1.in.readLine());
            p1.noResponse("");

            // Check if that input is valid.
            while (action == null) {
                p1.noResponse("Invalid input.");
                p1.noResponse("");
                p1.needResponse("What would you like to do?");
                action = Action.findMatch(p1.in.readLine());
            }
            
            // The only 2 actions a user can make currently are to attack,end their 
            // battle phase, or check the field as many times as they want.
            if (action == Action.ATTACK) {
                p1.attack(p2);
            } else if (action == Action.MAINPHASE) {
                mainPhase(p1, p2, 2);
            } else if (action == Action.CHECKFIELD) {
                p1.checkField(p2);
            } else if (action == Action.CHECKHAND) {
                p1.displayHand();
            } else if (action == Action.CHECKGY) {
                p1.checkGraveyard();
            } else if (action == Action.CHECKOPPGY) {
                p1.checkGraveyard(p2);
            }
        } while (action == null || action != Action.ENDTURN);

        // Enter the end phase.
        endPhase(p1, p2);
    }

    /**
     * The end phase for player 1. Card effects can be activated here, and if the turn player
     * has more than 6 cards in their hand, they must discard one.
     * @param p1 Player 1.
     * @param p2 Player 2.
     * @throws IOException If something goes wrong passing input/output with the clients.
     * @throws InterruptedException We use Thread.sleep() to make the duel feel more fluid.
     */
    public static void endPhase(Player p1, Player p2) throws IOException, InterruptedException {
        // Notify the players of the phase.
        p1.noResponse("End Phase");
        p2.noResponse("Opponent's End Phase");
        p1.noResponse("");
        p2.noResponse("");

        // If the turn player has more than 6 cards in their hand, they must discard one until
        // they have 6 cards.
        if (p1.hand.size() > 6) {
            while (p1.hand.size() > 6) {
                p1.noResponse("Choose a card to discard.");
                p1.noResponse("");
                // Display the player's hand so they know what their choices are.
                p1.displayHand();
                p1.needResponse("");
                // If the player gave an invalid card, loop the input until we receive a valid one.
                while (!p1.discard(p1.in.readLine())) {
                    p1.noResponse("Card not found.");
                    p1.noResponse("");
                    p1.noResponse("Choose a card to discard.");
                    p1.displayHand();
                    p1.needResponse("");
                }
            }
        // If the player doesn't need to discard cards, then let the phase sit for a bit
        // before moving on.
        } else {
            Thread.sleep(750);
        }

        // Reset all monster flags for the next turn.
        for (int i = 0; i < p1.monsterZones.size(); i++) {
            p1.monsterZones.get(i).switched = false;
            p1.monsterZones.get(i).attacked = false;
            p1.monsterZones.get(i).summoned = false;
        }
        for (int i = 0; i < p2.monsterZones.size(); i++) {
            p2.monsterZones.get(i).switched = false;
            p2.monsterZones.get(i).attacked = false;
            p2.monsterZones.get(i).summoned = false;
        }

        // Reset the player flags
        p1.normalSummoned = false;
        p2.normalSummoned = false;
        
        // If we've reached the end phase, that definitely means the next turn won't be the first.
        firstTurn = false;
        // Move onto the draw phase for the other player, switching the parameters.
        drawPhase(p2, p1);
    }

    /**
     * Done after every action, to see if there is a response from either player. Also checks
     * if a win condition has been met.
     * 
     * @param p1 Player 1.
     * @param p2 Player 2.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public static void checkForResponse(Player p1, Player p2) throws IOException {
        // If both player lifepoints reach 0 at the same time, it's a draw.
        if (p1.lifePoints <= 0 && p2.lifePoints <= 0) {
            draw(p1, p2);
        // If either player's lifepoints reach 0 while the other has some, it's a clear win.
        } else if (p1.lifePoints <= 0) {
            win(p2, p1);
        } else if (p2.lifePoints <= 0) {
            win(p1, p2);
        }
    }

    /**
     * Displays the winning messages and quits the program.
     * 
     * @param p1 Player 1.
     * @param p2 Player 2.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public static void win(Player p1, Player p2) throws IOException {
        p1.noResponse("You lose!");
        p2.noResponse("You win!");
        p1.noResponse("quit program");
        p1.noResponse("quit program");
        System.exit(0);
    }

    /**
     * Displays the drawing message and quits the program.
     * 
     * @param p1 Player 1.
     * @param p2 Player 2.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public static void draw(Player p1, Player p2) throws IOException {
        p1.noResponse("It's a draw!");
        p2.noResponse("It's a draw!");
        p1.noResponse("quit program");
        p1.noResponse("quit program");
        System.exit(0);
    }
}