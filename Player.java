package yugioh;

import java.io.*;

import java.net.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import yugioh.Card.*;
import yugioh.CardType;
import yugioh.MonsterZone;
import yugioh.PlayerBuilder;

/**
 * The player object contains nearly all the information about a given player, including the
 * cards in their deck, hand, and field, as well as their life points and whether or not
 * they have completed certain actions within that turn or not.
 * 
 * @author josefdewberry
 */
public class Player {
    
    // All the socket things which allow us to input/output with the player.
    public Socket s;
    public BufferedReader in;
    public BufferedReader keyboard;
    public PrintWriter out;
    public ObjectInputStream dataIn;
    public ObjectOutputStream dataOut;

    // All of the information dealing with where the player's cards are at a given moment.
    public ArrayList<Card> deck;
    public ArrayList<Card> extraDeck;
    public ArrayList<Card> hand;
    public ArrayList<MonsterZone> monsterZones;
    public ArrayList<Card> graveyard;

    // Misc. information about the player pertinent to the game.
    public boolean normalSummoned;
    int lifePoints;

    /**
     * Player constructor which sets up the input/output as well as basic
     * game things.
     * @param s The socket connection.
     */
    public Player(Socket s) throws IOException {

        // Set up the socket and all of its input/output.
        this.s = s;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(s.getOutputStream(), true);
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        dataOut = new ObjectOutputStream(s.getOutputStream());
        dataIn = new ObjectInputStream(s.getInputStream());
        
        // When the game starts we can assume that the player's hand and field will be empty.
        hand = new ArrayList<Card>();
        monsterZones = new ArrayList<MonsterZone>();
        graveyard = new ArrayList<Card>();

        // A player starts the game having NOT summoned, of course.
        normalSummoned = false;
        // Standard yugioh games have each player at 8000 life points.
        lifePoints = 8000;
    }

    /**
     * A static method allowing each player to build their deck from a .ydk file, which
     * the player gives. Both players can use the same deck file. We do this statically
     * because if we put it in the constructor then player 1 would have to input their
     * deck file before player 2, where as here we can do it simultaneously.
     * @param p1 Player 1.
     * @param p2 Player 2.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public static void buildDecks(Player p1, Player p2) throws IOException {
        // Get the name of the deck file from the users.
        p1.needResponse("What is your deck file?");
        p2.needResponse("What is your deck file?");
        File f1 = new File("./yugioh/Decks/" + p1.in.readLine());
        File f2 = new File("./yugioh/Decks/" + p2.in.readLine());

        // If the file doesn't exist, reprompt them until it does. Unfortunately, if both files
        // are invalid, player 2 won't know until player 1 inputs a valid one. This could be
        // solved with a third while loop which I COULD MAYBE DO LATER!!!!!!!
        while (!f1.exists()) {
            p1.noResponse("Sorry, I couldn't find that file.");
            p1.needResponse("What is your deck file?");
            f1 = new File("./yugioh/Decks/" + p1.in.readLine());
        }

        while (!f2.exists()) {
            p2.noResponse("Sorry, I couldn't find that file.");
            p2.needResponse("What is your deck file?");
            f2 = new File("./yugioh/Decks/" + p2.in.readLine());
        }

        // Build the deck and extra deck.
        p1.deck = PlayerBuilder.buildDeck(f1);
        p2.deck = PlayerBuilder.buildDeck(f2);

        // Let the user know their deck size and extra deck size. This is just a quick
        // and simple way for the user to know if something has gone wrong and their deck
        // isn't the size it should be.
        p1.noResponse("Deck size: " + p1.deck.size());
        p1.noResponse("Extra deck size: " + p1.extraDeck.size());

        p2.noResponse("Deck size: " + p2.deck.size());
        p2.noResponse("Extra deck size: " + p2.extraDeck.size());
    }

    /**
     * The method used when we want to print a message to the client but don't expect
     * a response.
     * 
     * @param s The message to be printed.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public void noResponse(String s) throws IOException {
        out.println(s);
        out.println("no response");
    }

    /**
     * The method we use when we want to print a message to the client and expect
     * an immediate response.
     * 
     * @param s The message to be printed.
     * @return The client's response.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public String needResponse(String s) throws IOException {
        out.println(s);
        out.println("respond");
        return in.readLine();
    }

    /**
     * Display the names of the cards in the player's hand.
     * 
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public void displayHand() throws IOException {
        noResponse("Hand:");
        for (int i = 0; i < hand.size(); i++) {
            noResponse(hand.get(i).getName());
        }
        noResponse("");
    }

    /**
     * Discard a card from the hand. Returns false if the card can't be found.
     * 
     * @param s The card name to be discarded.
     * @return Whether the card was successfully discarded.
     */
    public boolean discard(String s) {
        // Iterate over the player's hand to find the card.
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getName().equalsIgnoreCase(s)) {
                // A discarded card goes to the graveyard.
                graveyard.add(hand.remove(i));
                return true;
            }
        }
        return false;
    }

    /**
     * Shuffle the player's deck.
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Draw a card, moving it from the top of the deck to the hand. If there are no cards
     * to draw, then the drawing player loses.
     */
    public boolean draw() {
        if (deck.size() <= 0) {
            return false;
        } else {
            hand.add(deck.remove(0));
            return true;
        }
    }

    /**
     * Display the player's graveyard.
     * 
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public void checkGraveyard() throws IOException {

        // If there are no cards in the opponent's graveyard, display a special message.
        if (graveyard.size() == 0) {
            noResponse("There are no cards in your graveyard.");
            noResponse("");
        } else {
            noResponse("Graveyard:");
            noResponse("");
            for (int i = 0; i < graveyard.size(); i++) {
                noResponse(graveyard.get(i).getName());
            }
            noResponse("");
        }
    }

    /**
     * Display the opponent's graveyard.
     * 
     * @param p2 The opponent.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public void checkGraveyard(Player p2) throws IOException {

        // If there are no cards in the opponent's graveyard, display a special message.
        if (p2.graveyard.size() == 0) {
            noResponse("There are no cards in your opponent's graveyard.");
            noResponse("");
        } else {
            noResponse("Opponent's Graveyard:");
            noResponse("");
            for (int i = 0; i < p2.graveyard.size(); i++) {
                noResponse(p2.graveyard.get(i).getName());
            }
            noResponse("");
        }
    }

    /**
     * Display the field, which includes the cards currently played, the number of cards
     * in both player's graveyards, the number of cards in the opponent's hand, and both
     * player's lifepoints.
     * 
     * @param p2 The opponent.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public void checkField(Player p2) throws IOException {

        //Display the number of lifepoints the player has.
        noResponse("You have " + lifePoints + " life points.");
        noResponse("");

        // Display the number of cards in the player's deck.
        if (deck.size() == 1) {
            noResponse("You have 1 card in your deck.");
            noResponse("");
        } else {
            noResponse("You have " + deck.size() + " cards in your deck.");
            noResponse("");
        }

        // Display the monsters on the player's field.
        if (monsterZones.size() == 0) {
            noResponse("You have no monsters on your field.");
            noResponse("");
        } else {
            noResponse("Your Field:");
            noResponse("");
            for (int i = 0; i < monsterZones.size(); i++) {
                noResponse(monsterZones.get(i).card.getName() + monsterZones.get(i).position.toString());
            }
            noResponse("");
        }

        // Display the number of cards in the player's graveyard.
        if (graveyard.size() == 1) {
            noResponse("You have 1 card in your graveyard.");
            noResponse("");
        } else {
            noResponse("You have " + graveyard.size() + " cards in your graveyard");
            noResponse("");
        }

        // Display the opponent's life points.
        noResponse("Your opponent has " + p2.lifePoints + " life points.");
        noResponse("");

        // Display the number of cards in the opponent's hand.
        if (p2.hand.size() == 1) {
            noResponse("Your oppenent has 1 card in hand.");
            noResponse("");
        } else {
            noResponse("Your opponent has " + p2.hand.size() + " cards in hand.");
            noResponse("");
        }

        // Display the number of cards in the player's deck.
        if (p2.deck.size() == 1) {
            noResponse("Your opponent has 1 card in their deck.");
            noResponse("");
        } else {
            noResponse("Your opponent has " + p2.deck.size() + " cards in their deck.");
            noResponse("");
        }

        // Display the monsters on the opponent's field.
        if (p2.monsterZones.size() == 0) {
            noResponse("Your opponent has no monsters on their field.");
            noResponse("");
        } else {
            noResponse("Opponent's Field:");
            noResponse("");
            for (int i = 0; i < p2.monsterZones.size(); i++) {
                if (p2.monsterZones.get(i).position != Position.SET) {
                    noResponse(p2.monsterZones.get(i).card.getName() + p2.monsterZones.get(i).position.toString());
                } else {
                    noResponse("A set monster.");
                }
                noResponse("");
            }
        }

        // Display the number of cards in the opponent's graveyard.
        if (p2.graveyard.size() == 1) {
            noResponse("Your opponent has 1 card in their graveyard.");
            noResponse("");
        } else {
            noResponse("Your opponent has " + p2.graveyard.size() + " cards in their graveyard");
            noResponse("");
        }
    }

    /**
     * Switch the position of a monster.
     * 
     * @param p2 Player 2.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public void switchPosition(Player p2) throws IOException {

        // If there are no monster's to switch, back out.
        if (monsterZones.size() == 0) {
            noResponse("You have no monsters to switch position.");
            noResponse("");
            return;
        }

        // Get the user's input.
        needResponse("What monster would you like to switch?");
        String name = in.readLine();

        // Find the monster on the player's field.
        for (int i = 0; i < monsterZones.size(); i++) {
            if (monsterZones.get(i).card.getName().equalsIgnoreCase(name)) {
                // If the monster has already switched positions this turn, back out.
                if (monsterZones.get(i).switched) {
                    noResponse("That monster has already switched positions this turn.");
                    noResponse("");
                    return;
                }
                // If the monster has attacked this turn, back out.
                if (monsterZones.get(i).attacked) {
                    noResponse("Monsters cannot change position if they've attacked this turn.");
                    noResponse("");
                    return;
                }
                // If the monster was summoned this turn, back out.
                if (monsterZones.get(i).summoned) {
                    noResponse("Monsters cannot change position the turn they were summoned");
                    noResponse("");
                    return;
                }
                // Switch the position of the monster. Attack position monsters change to
                // face-up defense position.
                if (monsterZones.get(i).position == Position.ATK) {
                    monsterZones.get(i).position = Position.UPDEF;
                    noResponse("You switched your " + monsterZones.get(i).card.getName() + " to defense position");
                    p2.noResponse("Your opponent switched their " + monsterZones.get(i).card.getName() + " to defense position");
                // Face-up defense position monsters change to attack position.
                } else if (monsterZones.get(i).position == Position.UPDEF) {
                    monsterZones.get(i).position = Position.ATK;
                    noResponse("You switched your " + monsterZones.get(i).card.getName() + " to attack position");
                    p2.noResponse("Your opponent switched their " + monsterZones.get(i).card.getName() + " to attack position");
                // Set monsters change to face-up attack position.
                } else {
                    monsterZones.get(i).position = Position.ATK;
                    noResponse("You switched your set card " + monsterZones.get(i).card.getName() + " to attack position");
                    p2.noResponse("Your opponent switched their set card " + monsterZones.get(i).card.getName() + " to attack position");
                }
                // Set the monster's switched flag so it can't be switched again this turn.
                monsterZones.get(i).switched = true;
                noResponse("");
                p2.noResponse("");
                return;
            }
        }
        // If the monster can't be found, back out.
        noResponse("Monster not found.");
        noResponse("");
        return;
    }

    /**
     * Attack the opponent or the opponent's monster with the player's own monster.
     * 
     * @param p2 The opponent.
     * @return Whether the attack was successful.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public boolean attack(Player p2) throws IOException {

        // If the player has no monsters, back out.
        if (monsterZones.size() == 0) {
            noResponse("You have no monsters to attack with.");
            noResponse("");
            return false;
        }

        // Get the name of the monster to attack.
        needResponse("What monster would you like to attack with?");
        String name = in.readLine();

        // Find the monster to attack.
        for (int i = 0; i < monsterZones.size(); i++) {
            if (monsterZones.get(i).card.getName().equalsIgnoreCase(name)) {
                // If the monster has previously attacked, back out.
                if (monsterZones.get(i).attacked) {
                    noResponse("That monster already attacked.");
                    noResponse("");
                    return false;
                }
                // If the monster isn't in attack position, back out.
                if (monsterZones.get(i).position != Position.ATK) {
                    noResponse("That monster isn't in attack position.");
                    noResponse("");
                    return false;
                }
                // If the monster exists, proceed.
                MonsterCard attackingCard = (MonsterCard) monsterZones.get(i).card;
                // If the opponent has no monsters, attack directly!
                if (p2.monsterZones.size() == 0) {
                    noResponse(attackingCard.getName() + " attacks directly!");
                    p2.noResponse("Your opponent attacked you directly with " + attackingCard.getName());
                    monsterZones.get(i).attacked = true;
                    p2.lifePoints -= attackingCard.getAtk();
                    noResponse("Your opponent's lifepoints are now " + p2.lifePoints);
                    p2.noResponse("Your lifepoints are now " + p2.lifePoints);
                    Game.checkForResponse(this, p2);
                    return true;
                // If the opponent does have monsters, battle will have to be done.
                } else {
                    return battle(Player p2, int cardPosition);
                }
            }
        }
        noResponse("Monster not found.");
        noResponse("");
        return false;
    }

    public boolean battle(Player p2, int cardPosition) {

        // Make a separate monster card object for ease of access.
        MonsterCard attackingCard = (MonsterCard) monsterZones.get(i).card;

        // Get the monster to be attacked.
        needResponse("What monster would you like to attack?");
        String name = in.readLine();
        noReponse("");
        // If the player want's to attack a set monster, proceed.
        if (name.equalsIgnoreCase("set monster") || name.equalsIgnoreCase("a set monster")) {
            ArrayList<Card> setCards = new ArrayList<Card>();
            for (int j = 0; j < p2.monsterZones.size(); j++) {
                if (p2.monsterZones.get(j).position == Position.SET) {
                    setCards.add(p2.monsterZones.get(j).card);
                }
            }
            // If the player chose to attack a set monster but the opponent has none, back out.
            if (setCards.size() == 0) {
                noResponse("Your opponent has no set monsters.");
                noResponse("");
                return false;
            } else {
                // Display to the player the number of set monsters they have.
                noResponse("Your opponent has " + setCards.size() + " set monster(s).");
                // Get which monster they want to attack, order does matter.
                needResponse("Which would you like to attack?");
                String number = in.readLine();

                // Figure out if the player gave a valid input.
                try {
                    int setNum = Integer.parseInt(number);
                    // If the number isn't valid, back out.
                    if (setNum < 1 || setNum > setCards.size()) {
                        noResponse("Invalid input.");
                        noResponse("");
                        return false;
                    }
                    // Save the set card to be attacked for ease of access.
                    MonsterCard card = (MonsterCard) setCards.get(setNum - 1);
                    for (int j = 0; j < p2.monsterZones.size(); j++) {
                        // If the card is found, proceed.
                        if (p2.monsterZones.get(j).position == Position.SET && p2.monsterZones.get(j).card == card) {
                            p2.monsterZones.get(j).position = Position.UPDEF;
                            // Display the messages to the players.
                            noResponse(attackingCard.getName() + " attacks your opponent's set " + card.getName());
                            p2.noResponse("Your opponent's " + attackingCard.getName() + " attacks your set " + card.getName());
                            // The monster has declared an attack and cannot attack again this turn.
                            monsterZones.get(i).attacked = true;
                            // If the attacking monsters attack is greater than the set monster's defense,
                            // destroy the set monster.
                            if (attackingCard.getAtk() > card.getDef()) {
                                noResponse("Your opponent's monster was destroyed.");
                                p2.noResponse("Your monster was destroyed.");
                                noResponse("");
                                p2.noResponse("");
                                // Send the set monster to the graveyard.
                                p2.graveyard.add(p2.monsterZones.remove(j).card);
                            // If the attacking monsters attack is less than the set monster's defense,
                            // no card is destroyed but the attacking player takes the difference as damage.
                            } else if (attackingCard.getAtk() < card.getDef()) {
                                lifePoints -= (card.getDef() - attackingCard.getAtk());
                                noResponse("Your lifepoints are now " + lifePoints);
                                p2.noResponse("Your opponent's lifepoints are now " + p2.lifePoints);
                            }
                            // Check if either player just lost the game.
                            Game.checkForResponse(this, p2);
                            return true;
                        }
                    }
                // If the user didn't give a valid number input, back out.
                } catch (NumberFormatException nfe) {
                    noResponse("Invalid input.");
                    noResponse("");
                    return false;
                }
            }
        }
        // If the player names a monster, find it.
        for (int j = 0; j < p2.monsterZones.size(); j++) {
            if (p2.monsterZones.get(j).card.getName().equalsIgnoreCase(name) && p2.monsterZones.get(j).position != Position.SET) {
                // Make a separate monster card object for ease of access.
                MonsterCard defendingCard = (MonsterCard) p2.monsterZones.get(j).card;
                // Display the appropriate messages.
                noResponse(attackingCard.getName() + " attacks your opponent's " + defendingCard.getName());
                p2.noResponse("Your opponent's " + attackingCard.getName() + " attacks your " + defendingCard.getName());
                noResponse("");
                p2.noResponse("");
                // If the attacked monster is in attack position...
                if (p2.monsterZones.get(j).position == Position.ATK) {
                    // If both monsters attack values are the same, destroy both monsters.
                    // Neither player takes damage.
                    if (attackingCard.getAtk() == defendingCard.getAtk()) {
                        noResponse("Both monsters were destroyed.");
                        p2.noResponse("Both monsters were destroyed.");
                        noResponse("");
                        p2.noResponse("");
                        graveyard.add(monsterZones.remove(i).card);
                        p2.graveyard.add(p2.monsterZones.remove(j).card);
                    // If the attacking monster's attack value is greater, the opponent
                    // takes the difference as damage. Also the defending monster is destroyed.
                    } else if (attackingCard.getAtk() > defendingCard.getAtk()) {
                        noResponse("You destroyed your opponent's " + defendingCard.getName());
                        p2.noResponse("Your " + defendingCard.getName() + " was destroyed.");
                        noResponse("");
                        p2.noResponse("");
                        p2.graveyard.add(p2.monsterZones.remove(j).card);
                        monsterZones.get(i).attacked = true;
                        p2.lifePoints -= (attackingCard.getAtk() - defendingCard.getAtk());
                        noResponse("Your opponent's lifepoints are now " + p2.lifePoints);
                        p2.noResponse("Your lifepoints are now " + p2.lifePoints);
                    // If the attacking monsters attack value was less, destroy the attacking
                    // monster and the turn player takes the difference as damage.
                    } else {
                        noResponse("Your " + attackingCard.getName() + " was destroyed.");
                        p2.noResponse("Your opponent's " + attackingCard.getName() + " was destroyed.");
                        noResponse("");
                        p2.noResponse("");
                        graveyard.add(monsterZones.remove(i).card);
                        lifePoints -= (defendingCard.getAtk() - attackingCard.getAtk());
                        noResponse("Your lifepoints are now " + lifePoints);
                        p2.noResponse("Your opponent's lifepoints are now " + lifePoints);
                    }
                    // Check if either player lost the game.
                    Game.checkForResponse(this, p2);
                    return true;
                // If the defending monster is in defense position...
                } else if (p2.monsterZones.get(j).position == Position.UPDEF) {
                    // If the attacking monster's attack is greater than the defending monster's
                    // defense, the defending monster is destroyed and nobody takes damage.
                    if (attackingCard.getAtk() > defendingCard.getDef()) {
                        noResponse("Your opponent's monster was destroyed.");
                        p2.noResponse("Your monster was destroyed.");
                        noResponse("");
                        p2.noResponse("");
                        p2.graveyard.add(p2.monsterZones.remove(j).card);
                    // If the attacking monster's attack is less than the defending monster's
                    // defense, neither monster is destroyed but the turn player takes the
                    // difference as damage.
                    } else if (attackingCard.getAtk() < defendingCard.getDef()) {
                        lifePoints -= (attackingCard.getDef() - defendingCard.getAtk());
                        noResponse("Your lifepoints are now " + lifePoints);
                        p2.noResponse("Your opponent's lifepoints are now " + p2.lifePoints);
                    }
                    // If the attacking monster's attack is equal to the defending monster's
                    // defense, nothing happens.

                    // Check if either player lost the game.
                    Game.checkForResponse(this, p2);
                    return true;
                }
            }
        }

        // If the monster couldn't be found, back out.
        noResponse("Monster not found.");
        noResponse("");
        return false;
    }

    /**
     * Normal summon a monster if possible.
     * 
     * @param p2 The opponent.
     * @return Whether the normal summon was done successfully.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public boolean normalSummon(Player p2) throws IOException {

        // If the player has used their normal summon they can't summon again.
        if (normalSummoned) {
            noResponse("You've already normal summoned this turn.");
            noResponse("");
            return true;
        }

        // If the field is full the player can't normal summon.
        if (monsterZones.size() >= 5) {
            noResponse("There is no room to summon a monster.");
            noResponse("");
            return false;
        }

        // Get the user's input.
        needResponse("What monster would you like to normal summon?");
        String name = in.readLine();

        // Fine the matching card in the user's hand.
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getName().equalsIgnoreCase(name)) {
                // If the card is found, make a duplicate for ease of access.
                if (hand.get(i).getCardType() == CardType.MONSTER) {
                    MonsterCard card = (MonsterCard) hand.get(i);
                    // If the level is greater than 4 then the user needs to tribute for that
                    // monster.
                    if (card.getLevel() > 4) {
                        noResponse("You need to tribute summon that monster.");
                        noResponse("");
                        return false;
                    // If the proper card was found, ask what position to summon the monster.
                    } else {
                        needResponse("(atk) or face-down (def) position?");
                        Position position = Position.findMatch(in.readLine());
                        // If the position input was invalid, back out of the summon.
                        if (position == null) {
                            noResponse("Invalid input.");
                            noResponse("");
                            return false;
                        }

                        // If the position was valid, display the proper messages.
                        if (position == Position.ATK) {
                            noResponse("You normal summoned " + card.getName() + ".");
                            p2.noResponse("Your opponent normal summoned " + card.getName() + ".");
                        } else {
                            noResponse("You set " + card.getName());
                            p2.noResponse("Your opponent set a monster.");

                        }
                        noResponse("");
                        p2.noResponse("");
                        monsterZones.add(new MonsterZone(hand.remove(i), position));
                        normalSummoned = true;
                        return true;
                    }
                // If the card was found but isn't a monster, back out of the summon.
                } else {
                    noResponse("That's not a monster.");
                    noResponse("");
                    return false;
                }
            }
        }
        // If the card couldn't be found, back out of the summon.
        noResponse("Card not found.");
        noResponse("");
        return false;
    }

    /**
     * Tribute summon a monster of level 5 or higher.
     * 
     * @param p2 The opponent.
     * @return Whether the monster was successfully summoned.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public boolean tributeSummon(Player p2) throws IOException {
        // If the player has already summoned that turn, back out.
        if (normalSummoned) {
            noResponse("You've already normal summoned this turn.");
            noResponse("");
            return false;
        }
        // If there are no monsters on the field to tribute, back out.
        if (monsterZones.size() == 0) {
            noResponse("You have no monsters to tribute.");
            noResponse("");
            return false;
        }

        // Get the name of the monster.
        needResponse("What monster would you like to tribute summon?");
        String name = in.readLine();

        // Search for the monster in the hand.
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getName().equalsIgnoreCase(name)) {
                // If the card is in fact a monster, proceed.
                if (hand.get(i).getCardType() == CardType.MONSTER) {
                    MonsterCard card = (MonsterCard) hand.get(i);
                    // If the monster doesn't require tributes, back out.
                    if (card.getLevel() <= 4) {
                        noResponse("You don't need to tribute for that monster.");
                        noResponse("");
                        return false;
                    // If the monster requires 1 tribute, proceed.
                    } else if (card.getLevel() == 5 || card.getLevel() == 6) {
                        return(minorTribute(p2, i));
                    // If the monster requires 2 tributes, proceed.    
                    } else {
                        return(majorTribute(p2, i));
                    }
                // If the given card isn't a monster, back out.
                } else {
                    noResponse("That's not a monster.");
                    noResponse("");
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Tribute summon a monster of level 5 or 6, which only requires 1 tribute.
     * 
     * @param p2 The opponent
     * @param cardPosition The position of the monster to be summoned in the player's hand.
     * @return Whether the monster was successfully summoned.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public boolean minorTribute(Player p2, int cardPosition) throws IOException {

        // Set up a separate card object for ease of access.
        MonsterCard card = (MonsterCard) hand.get(cardPosition);

        // Get the name of the tribute material.
        needResponse("Select a monster to be tributed.");
        noResponse("");
        String tribName = in.readLine();
        // Find the tribute material on the field.
        for (int j = 0; j < monsterZones.size(); j++) {
            if (tribName.equalsIgnoreCase(monsterZones.get(j).card.getName())) {
                // Get the position the monster will be summoned in.
                needResponse("(atk) or face-down (def) position?");
                Position position = Position.findMatch(in.readLine());
                // If the user gives an invalid position to summon, back out.
                if (position == null) {
                    noResponse("Invalid input.");
                    noResponse("");
                    return false;
                }
                // Send the tribute material to the grave.
                graveyard.add(monsterZones.get(j).card);
                monsterZones.remove(j);
                // Add the monster to the field, effectively summoning it.
                monsterZones.add(new MonsterZone(hand.remove(cardPosition), position));
                // Display the proper messages to the players based upon the monster's position.
                if (position == Position.ATK) {
                    noResponse("You tribute summoned " + card.getName());
                    noResponse("");
                    p2.noResponse("Your opponent tributed 1 monster for " + card.getName());
                    normalSummoned = true;
                    return true;
                } else {
                    noResponse("You set " + card.getName());
                    noResponse("");
                    p2.noResponse("Your opponent tributed 1 monster to set a monster.");
                    normalSummoned = true;
                    return true;

                }
            }
        }
        // If the tribute material couldn't be found, back out.
        noResponse("Monster not found.");
        noResponse("");
        return false;
    }

    /**
     * Tribute summon a monster of level 8 or higher, requiring 2 tribute materials.
     * 
     * @param p2 The opponent.
     * @param cardPosition The position of the monster to be summoned in the player's hand.
     * @return Whether the monster was successfully summoned.
     * @throws IOException If something goes wrong passing input/output with the clients.
     */
    public boolean majorTribute(Player p2, int cardPosition) throws IOException {

        // If there isn't enough tribute material, back out.
        if (monsterZones.size() < 2) {
            noResponse("You don't have enough monsters to tribute.");
            noResponse("");
            return false;
        }

        // Set up a separate card object for ease of access.
        MonsterCard card = (MonsterCard) hand.get(cardPosition);

        // Get the name of the first tribute material.
        needResponse("Select a monster to be tributed.");
        needResponse("");
        String tribName = in.readLine();
        // FInd the tribute material.
        for (int j = 0; j < monsterZones.size(); j++) {
            if (tribName.equalsIgnoreCase(monsterZones.get(j).card.getName())) {
                // This is jerry-rigged because I have the monsterZones as an arraylist
                // instead of an array. This would actually be much easier with an array
                // AND I SHOULD DO THAT ASAP!!!!!!
                Position tempPosition = monsterZones.get(j).position;
                graveyard.add(monsterZones.get(j).card);
                monsterZones.remove(monsterZones.get(j));
                // Find the second tribute material.
                needResponse("Select a second monster to be tributed.");
                String tribName2 = in.readLine();
                for (int k = 0; k < monsterZones.size(); k++) {
                    // If both of the tribute material were found.
                    if (tribName2.equalsIgnoreCase(monsterZones.get(k).card.getName())) {
                        // Get the position the monster will be summoned in.
                        needResponse("(atk) or face-down (def) position?");
                        Position position = Position.findMatch(in.readLine());
                        // If the player gave an invalid position, back out.
                        if (position == null) {
                            noResponse("Invalid input.");
                            noResponse("");
                            monsterZones.add(new MonsterZone(graveyard.remove(graveyard.size() - 1), tempPosition));
                            return false;
                        // If the position is valid, summon the monster.
                        } else {
                            // Send the tribute material to the grave.
                            graveyard.add(monsterZones.get(k).card);
                            monsterZones.remove(k);
                            // Add the monster to the field, effectively summoning it.
                            monsterZones.add(new MonsterZone(hand.remove(cardPosition), position));
                            // Display the appropriate messages considering the monster's position.
                            if (position == Position.ATK) {
                                noResponse("You tribute summoned " + card.getName());
                                noResponse("");
                                p2.noResponse("Your opponent tributed 2 monsters for " + card.getName());
                                normalSummoned = true;
                                return true;
                            } else {
                                noResponse("You set " + card.getName());
                                noResponse("");
                                p2.noResponse("Your opponent tributed 2 monsters to set a monster.");
                                normalSummoned = true;
                                return true;

                            }
                        }
                    }
                }
                // If the second monster couldn't be found, re-summon the first. This actually creates a bug
                // where a monster could infinitely attack or not switch positions despite not being
                // summoned this turn and should BE FIXED IMMEDIATELY!!!!!!!
                monsterZones.add(new MonsterZone(graveyard.remove(graveyard.size() - 1), tempPosition));
                noResponse("Monster not found.");
                noResponse("");
                return false;
            }
        }
        // If the monster to be summoned can't be found, back out.
        noResponse("Monster not found.");
        noResponse("");
        return false;
    }
}