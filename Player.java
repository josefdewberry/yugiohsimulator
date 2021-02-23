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

public class Player {
    
    public Socket s;
    public BufferedReader in;
    public BufferedReader keyboard;
    public PrintWriter out;
    public ObjectInputStream dataIn;
    public ObjectOutputStream dataOut;

    public ArrayList<Card> deck;
    public ArrayList<Card> extraDeck;
    public ArrayList<Card> hand;
    public ArrayList<MonsterZone> monsterZones;
    public ArrayList<Card> graveyard;

    public boolean normalSummoned;
    int lifePoints;

    public Player(Socket s) throws IOException {
        this.s = s;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(s.getOutputStream(), true);
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        dataOut = new ObjectOutputStream(s.getOutputStream());
        dataIn = new ObjectInputStream(s.getInputStream());
        
    }

    public static void buildDecks(Player p1, Player p2) throws IOException {
        p1.needResponse("What is your deck file?");
        p2.needResponse("What is your deck file?");
        File f1 = new File("./yugioh/Decks/" + p1.in.readLine());
        File f2 = new File("./yugioh/Decks/" + p2.in.readLine());

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

        p1.deck = PlayerBuilder.buildDeck(f1);
        p1.extraDeck = PlayerBuilder.buildExtraDeck(f1);

        p2.deck = PlayerBuilder.buildDeck(f2);
        p2.extraDeck = PlayerBuilder.buildExtraDeck(f2);

        p1.noResponse("Deck size: " + p1.deck.size());
        p1.noResponse("Extra deck size: " + p1.extraDeck.size());

        p2.noResponse("Deck size: " + p2.deck.size());
        p2.noResponse("Extra deck size: " + p2.extraDeck.size());

        p1.hand = new ArrayList<Card>();
        p2.hand = new ArrayList<Card>();

        p1.monsterZones = new ArrayList<MonsterZone>();
        p2.monsterZones = new ArrayList<MonsterZone>();

        p1.graveyard = new ArrayList<Card>();
        p2.graveyard = new ArrayList<Card>();

        p1.normalSummoned = false;
        p2.normalSummoned = false;

        p1.lifePoints = 8000;
        p2.lifePoints = 8000;
    }

    public void noResponse(String s) throws IOException {
        out.println(s);
        out.println("no response");
    }

    public void needResponse(String s) throws IOException {
        out.println(s);
        out.println("respond");
    }

    public void displayHand() throws IOException {
        noResponse("");
        noResponse("Hand:");
        for (int i = 0; i < hand.size(); i++) {
            noResponse(hand.get(i).getName());
        }
    }

    public boolean discard(String s) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getName().equalsIgnoreCase(s)) {
                graveyard.add(hand.remove(i));
                return true;
            }
        }
        return false;
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void draw() {
        hand.add(deck.remove(0));
    }

    public void checkGraveyard() throws IOException {

        if (graveyard.size() == 0) {
            noResponse("");
            noResponse("There are no cards in your graveyard.");
        } else {
            noResponse("");
            noResponse("Graveyard:");
            for (int i = 0; i < graveyard.size(); i++) {
                noResponse(graveyard.get(i).getName());
            }
        }
    }

    public void checkGraveyard(Player p2) throws IOException {

        if (p2.graveyard.size() == 0) {
            noResponse("");
            noResponse("There are no cards in your opponent's graveyard.");
        } else {
            noResponse("");
            noResponse("Opponent's Graveyard:");
            for (int i = 0; i < p2.graveyard.size(); i++) {
                noResponse(p2.graveyard.get(i).getName());
            }
        }
    }

    public void checkField(Player p2) throws IOException {

        noResponse("");

        if (monsterZones.size() == 0) {
            noResponse("");
            noResponse("You have no monsters on your field.");
        } else {
            noResponse("");
            noResponse("Your Field:");
            for (int i = 0; i < monsterZones.size(); i++) {
                noResponse(monsterZones.get(i).card.getName() + Position.toString(monsterZones.get(i).position));
            }
        }

        if (graveyard.size() == 1) {
            noResponse("");
            noResponse("You have 1 card in your graveyard.");
        } else {
            noResponse("");
            noResponse("You have " + graveyard.size() + " cards in your graveyard");
        }

        if (p2.monsterZones.size() == 0) {
            noResponse("");
            noResponse("Your opponent has no monsters on their field.");
        } else {
            noResponse("");
            noResponse("Opponent's Field:");
            for (int i = 0; i < p2.monsterZones.size(); i++) {
                if (p2.monsterZones.get(i).position != Position.SET) {
                    noResponse(p2.monsterZones.get(i).card.getName() + Position.toString(p2.monsterZones.get(i).position));
                } else {
                    noResponse("A set monster.");
                }
            }
        }

        if (p2.graveyard.size() == 1) {
            noResponse("");
            noResponse("Your opponent has 1 card in their graveyard.");
        } else {
            noResponse("");
            noResponse("Your opponent has " + p2.graveyard.size() + " cards in their graveyard");
        }

        if (p2.hand.size() == 1) {
            noResponse("");
            noResponse("Your oppenent has 1 card in hand.");
        } else {
            noResponse("");
            noResponse("Your opponent has " + p2.hand.size() + " cards in hand.");
        }
        noResponse("");
    }

    public void switchPosition(Player p2) throws IOException {

        if (monsterZones.size() == 0) {
            noResponse("You have no monsters to switch position.");
            noResponse("");
            return;
        }
        needResponse("What monster would you like to switch?");
        String name = in.readLine();

        for (int i = 0; i < monsterZones.size(); i++) {
            if (monsterZones.get(i).card.getName().equalsIgnoreCase(name)) {
                if (monsterZones.get(i).switched) {
                    noResponse("That monster has already switched positions this turn.");
                    noResponse("");
                    return;
                }
                if (monsterZones.get(i).attacked) {
                    noResponse("Monsters cannot change position if they've attacked this turn.");
                    noResponse("");
                    return;
                }
                if (monsterZones.get(i).position == Position.ATK) {
                    monsterZones.get(i).position = Position.UPDEF;
                    noResponse("You switched your " + monsterZones.get(i).card.getName() + " to defense position");
                    p2.noResponse("Your opponent switched their " + monsterZones.get(i).card.getName() + " to defense position");
                } else if (monsterZones.get(i).position == Position.UPDEF) {
                    monsterZones.get(i).position = Position.ATK;
                    noResponse("You switched your " + monsterZones.get(i).card.getName() + " to attack position");
                    p2.noResponse("Your opponent switched their " + monsterZones.get(i).card.getName() + " to attack position");
                } else {
                    monsterZones.get(i).position = Position.ATK;
                    noResponse("You switched your set card " + monsterZones.get(i).card.getName() + " to attack position");
                    p2.noResponse("Your opponent switched their set card " + monsterZones.get(i).card.getName() + " to attack position");
                }
                monsterZones.get(i).switched = true;
                return;
            }
        }
        noResponse("Card not found.");
        noResponse("");
        return;
    }

    public boolean attack(Player p2) throws IOException {

        if (monsterZones.size() == 0) {
            noResponse("You have no monsters to attack with.");
            noResponse("");
            return false;
        }

        needResponse("What monster would you like to attack with?");
        String name = in.readLine();

        for (int i = 0; i < monsterZones.size(); i++) {
            if (monsterZones.get(i).card.getName().equalsIgnoreCase(name)) {
                if (monsterZones.get(i).attacked) {
                    noResponse("That monster already attacked.");
                    noResponse("");
                    return false;
                }
                if (monsterZones.get(i).position != Position.ATK) {
                    noResponse("That monster isn't in attack position.");
                    noResponse("");
                    return false;
                }
                MonsterCard attackingCard = (MonsterCard) monsterZones.get(i).card;
                if (p2.monsterZones.size() == 0) {
                    noResponse(attackingCard.getName() + " attacks directly!");
                    p2.noResponse("Your opponent attacked you directly with " + attackingCard.getName());
                    monsterZones.get(i).attacked = true;
                    p2.lifePoints -= attackingCard.getAtk();
                    noResponse("Your opponent's lifepoints are now " + p2.lifePoints);
                    p2.noResponse("Your lifepoints are now " + p2.lifePoints);
                    Game.checkForResponse(this, p2);
                    return true;
                } else {
                    needResponse("What monster would you like to attack?");
                    name = in.readLine();
                    if (name.equalsIgnoreCase("set monster")) {
                        ArrayList<Card> setCards = new ArrayList<Card>();
                        for (int j = 0; j < p2.monsterZones.size(); j++) {
                            if (p2.monsterZones.get(j).position == Position.SET) {
                                setCards.add(p2.monsterZones.get(j).card);
                            }
                        }
                        if (setCards.size() == 0) {
                            noResponse("Your opponent has no set monsters.");
                            noResponse("");
                            return false;
                        } else {
                            noResponse("Your opponent has " + setCards.size() + " set monster(s).");
                            needResponse("Which would you like to attack?");
                            String number = in.readLine();

                            try {
                                int setNum = Integer.parseInt(number);
                                if (setNum < 1 || setNum > setCards.size()) {
                                    noResponse("Invalid input.");
                                    noResponse("");
                                    return false;
                                }
                                MonsterCard card = (MonsterCard) setCards.get(setNum - 1);
                                for (int j = 0; j < p2.monsterZones.size(); j++) {
                                    if (p2.monsterZones.get(j).position == Position.SET && p2.monsterZones.get(j).card == card) {
                                        p2.monsterZones.get(j).position = Position.UPDEF;
                                        noResponse(attackingCard.getName() + " attacks your opponent's set " + card.getName());
                                        p2.noResponse("Your opponent's " + attackingCard.getName() + " attacks your set " + card.getName());
                                        monsterZones.get(i).attacked = true;
                                        if (attackingCard.getAtk() > card.getDef()) {
                                            noResponse("Your opponent's monster was destroyed.");
                                            p2.noResponse("Your monster was destroyed.");
                                            noResponse("");
                                            p2.noResponse("");
                                            p2.graveyard.add(p2.monsterZones.remove(j).card);
                                        } else if (attackingCard.getAtk() < card.getDef()) {
                                            lifePoints -= (card.getDef() - attackingCard.getAtk());
                                            noResponse("Your lifepoints are now " + lifePoints);
                                            p2.noResponse("Your opponent's lifepoints are now " + p2.lifePoints);
                                        }
                                        Game.checkForResponse(this, p2);
                                        return true;
                                    }
                                }
                            } catch (NumberFormatException nfe) {
                                noResponse("Invalid input.");
                                noResponse("");
                                return false;
                            }
                        }
                    }
                    for (int j = 0; j < p2.monsterZones.size(); j++) {
                        if (p2.monsterZones.get(j).card.getName().equalsIgnoreCase(name) && p2.monsterZones.get(j).position != Position.SET) {
                            MonsterCard defendingCard = (MonsterCard) p2.monsterZones.get(j).card;
                            noResponse(attackingCard.getName() + " attacks your opponent's " + defendingCard.getName());
                            p2.noResponse("Your opponent's " + attackingCard.getName() + " attacks your " + defendingCard.getName());

                            if (p2.monsterZones.get(j).position == Position.ATK) {
                                if (attackingCard.getAtk() == defendingCard.getAtk()) {
                                    noResponse("Both monsters were destroyed.");
                                    p2.noResponse("Both monsters were destroyed.");
                                    noResponse("");
                                    p2.noResponse("");
                                    graveyard.add(monsterZones.remove(i).card);
                                    p2.graveyard.add(p2.monsterZones.remove(j).card);
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
                                Game.checkForResponse(this, p2);
                                return true;
                            } else if (p2.monsterZones.get(j).position == Position.UPDEF) {
                                if (attackingCard.getAtk() > defendingCard.getDef()) {
                                    noResponse("Your opponent's monster was destroyed.");
                                    p2.noResponse("Your monster was destroyed.");
                                    noResponse("");
                                    p2.noResponse("");
                                    p2.graveyard.add(p2.monsterZones.remove(j).card);
                                } else if (attackingCard.getAtk() < defendingCard.getDef()) {
                                    lifePoints -= (attackingCard.getDef() - defendingCard.getAtk());
                                    noResponse("Your lifepoints are now " + lifePoints);
                                    p2.noResponse("Your opponent's lifepoints are now " + p2.lifePoints);
                                }
                                Game.checkForResponse(this, p2);
                                return true;
                            }
                        }
                    }

                    noResponse("Card not found.");
                    noResponse("");
                    return false;
                }
            }
        }
        noResponse("Card not found.");
        noResponse("");
        return false;
    }

    public boolean normalSummon(Player p2) throws IOException {

        if (normalSummoned) {
            noResponse("You've already normal summoned this turn.");
            noResponse("");
            return true;
        }

        if (monsterZones.size() >= 5) {
            noResponse("There is no room to summon a monster.");
            noResponse("");
            return false;
        }

        needResponse("What monster would you like to normal summon?");
        String name = in.readLine();

        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getName().equalsIgnoreCase(name)) {
                if (hand.get(i).getCardType() == CardType.MONSTER) {
                    MonsterCard card = (MonsterCard) hand.get(i);
                    if (card.getLevel() > 4) {
                        noResponse("You need to tribute summon that monster.");
                        noResponse("");
                        return false;
                    } else {
                        needResponse("(atk) or face-down (def) position?");
                        Position position = Position.findMatch(in.readLine());
                        if (position == null) {
                            noResponse("Invalid input.");
                            noResponse("");
                            return false;
                        }

                        if (position == Position.ATK) {
                            noResponse("You normal summoned " + card.getName());
                            p2.noResponse("Your opponent normal summoned " + card.getName());
                        } else {
                            noResponse("You set " + card.getName());
                            p2.noResponse("Your opponent set a monster");

                        }
                        monsterZones.add(new MonsterZone(hand.remove(i), position));
                        normalSummoned = true;
                        return true;
                    }
                } else {
                    noResponse("That's not a monster.");
                    noResponse("");
                    return false;
                }
            }
        }

        return false;
    }

    public boolean tributeSummon(Player p2) throws IOException {
        if (normalSummoned) {
            noResponse("You've already normal summoned this turn.");
            noResponse("");
            return false;
        }

        if (monsterZones.size() >= 5) {
            noResponse("There is no room to summon a monster.");
            noResponse("");
            return false;
        } else if (monsterZones.size() == 0) {
            noResponse("You have no monsters to tribute.");
            noResponse("");
            return false;
        }

        needResponse("What monster would you like to tribute summon?");
        String name = in.readLine();

        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getName().equalsIgnoreCase(name)) {
                if (hand.get(i).getCardType() == CardType.MONSTER) {
                    MonsterCard card = (MonsterCard) hand.get(i);
                    if (card.getLevel() <= 4) {
                        noResponse("You don't need to tribute for that monster.");
                        noResponse("");
                        return false;
                    } else if (card.getLevel() == 5 || card.getLevel() == 6) {
                        needResponse("Select a monster to be tributed.");
                        String tribName = in.readLine();
                        for (int j = 0; j < monsterZones.size(); j++) {
                            if (tribName.equalsIgnoreCase(monsterZones.get(j).card.getName())) {
                                needResponse("(atk) or face-down (def) position?");
                                Position position = Position.findMatch(in.readLine());
                                if (position == null) {
                                    noResponse("Invalid input.");
                                    noResponse("");
                                    return false;
                                }
                                graveyard.add(monsterZones.get(j).card);
                                monsterZones.remove(j);
                                monsterZones.add(new MonsterZone(hand.remove(i), position));
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
                        noResponse("Card not found.");
                        noResponse("");
                        return false;
                    } else {

                        if (monsterZones.size() < 2) {
                            noResponse("You don't have enough monsters to tribute.");
                            noResponse("");
                            return false;
                        }

                        needResponse("Select a monster to be tributed.");
                        String tribName = in.readLine();
                        for (int j = 0; j < monsterZones.size(); j++) {
                            if (tribName.equalsIgnoreCase(monsterZones.get(j).card.getName())) {
                                Position tempPosition = monsterZones.get(j).position;
                                graveyard.add(monsterZones.get(j).card);
                                monsterZones.remove(monsterZones.get(j));
                                needResponse("Select a second monster to be tributed.");
                                String tribName2 = in.readLine();
                                for (int k = 0; k < monsterZones.size(); k++) {
                                    if (tribName2.equalsIgnoreCase(monsterZones.get(k).card.getName())) {
                                        needResponse("(atk) or face-down (def) position?");
                                        Position position = Position.findMatch(in.readLine());
                                        if (position == null) {
                                            noResponse("Invalid input.");
                                            noResponse("");
                                            monsterZones.add(new MonsterZone(graveyard.remove(graveyard.size() - 1), tempPosition));
                                            return false;
                                        } else {
                                            graveyard.add(monsterZones.get(k).card);
                                            monsterZones.remove(k);
                                            monsterZones.add(new MonsterZone(hand.remove(i), position));
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
                                monsterZones.add(new MonsterZone(graveyard.remove(graveyard.size() - 1), tempPosition));
                                noResponse("Card not found.");
                                noResponse("");
                                return false;
                            }
                        }
                        noResponse("Card not found.");
                        noResponse("");
                        return false;

                    }
                } else {
                    noResponse("That's not a monster.");
                    noResponse("");
                    return false;
                }
            }
        }
        return false;
    }
}