package yugioh;

import java.io.File;

import java.util.ArrayList;
import java.util.Scanner;

import yugioh.Card;
import yugioh.Player;

/**
 * A class that does some of the heavy lifting when it comes to building a player object.
 * 
 * @author josefdewberry
 */
public class PlayerBuilder {
    
    /**
     * Builds the player's main deck (40 to 60 cards) from the given file.
     * 
     * @param f The properly formatted .ydk file.
     * @return The deck.
     */
    public static ArrayList<Card> buildDeck(File f) {
        
        // Reading files can go poorly.
        try {

            Scanner scanner = new Scanner(f);
            // The first two lines deal with the owner of the deck and a denotion
            // that the following cards are the main deck.
            scanner.nextLine();
            scanner.nextLine();

            // Set up the list of available cards to make the deck from.
            ArrayList<Card> cards = CardReader.readCards();

            // Set up the empty deck.
            ArrayList<Card> deck = new ArrayList<Card>();
            ArrayList<Card> extraDeck = new ArrayList<Card>();
            
            // Read from the file...
            while (scanner.hasNextLine()) {

                // Until we hit the extra deck section.
                String tempCode = scanner.nextLine();
                if (tempCode.equals("#extra")) {
                    // Build the extra deck.
                    tempCode = scanner.nextLine();
                    // Currently there is no functionality for a side deck, which is used
                    // in best 2 out of 3's.
                    while (!tempCode.equals("!side")) {
                        int code = Integer.parseInt(tempCode);
                        for (int i = 0; i < cards.size(); i++) {
                            if (cards.get(i).getCode() == code) {
                                extraDeck.add(cards.get(i));
                                tempCode = scanner.nextLine();
                            }
                        }
                    }
                }
                
                // Add the cards 1 by 1 into the deck.
                int code = Integer.parseInt(tempCode);
                for (int i = 0; i < cards.size(); i++) {
                    if (cards.get(i).getCode() == code) {
                        deck.add(cards.get(i));
                        break;
                    }
                }

            }

            // Return the constructed deck.
            return deck;

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}