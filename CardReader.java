package yugioh;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import yugioh.Card.*;
import yugioh.CardType;

/**
 * Reads cards from a .tsv file and builds a database of card objects from it.
 * 
 * @author josefdewberry
 */
public class CardReader {

    /**
     * Build the monster card with the appropriately given properties.
     * 
     * @param s The monster card as a string with tabs separating the properties.
     * @return The built monster card.
     */
    public static Card buildMonster(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("\t");

        // No properties are skipped here.
        CardType cardType = CardType.findMatch(scanner.next());
        String name = scanner.next();
        String text = scanner.next();
        String type = scanner.next();
        Attribute attribute = Attribute.findMatch(scanner.next());
        int level = Integer.parseInt(scanner.next());
        int atk = Integer.parseInt(scanner.next());
        int def = Integer.parseInt(scanner.next());
        int code = Integer.parseInt(scanner.next());
        String number = scanner.next();
        Rarity rarity = Rarity.findMatch(scanner.next());

        // Memory leaks!
        scanner.close();

        // A monster always has multiple types, so we need to cycle through them
        // with a separate scanner.
        Scanner typeScanner = new Scanner(type);
        typeScanner.useDelimiter("/");
        
        ArrayList<Type> tempTypes = new ArrayList();
        while (typeScanner.hasNext()) {
            tempTypes.add(Type.findMatch(typeScanner.next()));
        }

        // Memory leaks.
        typeScanner.close();

        // Construct the type array from the separated types.
        Type[] types = new Type[tempTypes.size()];
        
        for (int i = 0; i < tempTypes.size(); i++) {
            types[i] = tempTypes.get(i);
        }

        // Build and return the monster card.
        Card card = new MonsterCard(name, cardType, text, number, code, rarity, attribute,
                                    level, types, atk, def);
        return card;
    }

    /**
     * Create a spell/trap card given a string of it's properties.
     * 
     * @param s The card as a string.
     * @return The build card.
     */
    public static Card buildBackrow(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("\t");

        // The first 4 values in the string are needed.
        CardType cardType = CardType.findMatch(scanner.next());
        String name = scanner.next();
        String text = scanner.next();
        Property property = Property.findMatch(scanner.next());
        // These skipped values are simply monster only properties that we can skip over.
        // In the string it's just tab after tab after tab.
        scanner.next();
        scanner.next();
        scanner.next();
        scanner.next();
        int code = Integer.parseInt(scanner.next());
        String number = scanner.next();
        Rarity rarity = Rarity.findMatch(scanner.next());

        // Memory leaks!
        scanner.close();
    
        // Build and return the card.
        Card card = new BackrowCard(name, cardType, text, number, code, rarity, property);
        return card;
    }

    /**
     * From the set list, read an individual card and add it to cards.
     * 
     * @param s All the cards as a properly formatted string.
     * @param cards The list to put the cards into.
     */
    public static void readCard(String s, ArrayList cards) {
        Scanner scanner = new Scanner(s);
        // Its a tab-spaced file so set the delimiter to a tab.
        scanner.useDelimiter("\t");
        // The cardtype is the first value entered, important in letting us know
        // how to build the card.
        CardType cardType = CardType.findMatch(scanner.next());

        // If the card is a monster, build it as such.
        if (cardType == CardType.MONSTER) {
            cards.add(buildMonster(s));
        // If the cards is a spell or trap, build it as such.
        } else if (cardType == CardType.SPELL || cardType == CardType.TRAP) {
            cards.add(buildBackrow(s));
        }

        // Close the scanner for memory leaks.
        scanner.close();
    }

    /**
     * The initially called method which builds all the cards.
     * 
     * @return All the cards currently in the Set List folder.
     */
    public static ArrayList<Card> readCards() {
        
        // The arraylist to hold all the cards.
        ArrayList cards = new ArrayList();

        // Reading files requires exceptions!
        try {
            
            // The folder which has the different sets of cards.
            File f = new File("./yugioh/Set Lists");

            // Read every single file in the folder.
            for (File fileEntry : f.listFiles()) {
                File file = new File("./yugioh/Set Lists/" + fileEntry.getName());
                Scanner fileScanner = new Scanner(file);
                fileScanner.nextLine();
                // Read the individual set.
                while (fileScanner.hasNextLine()) {
                    readCard(fileScanner.nextLine(), cards);
                }

                // Close the scanner to prevent memory leak.
                fileScanner.close();
            } 

        // Throw if something goes wrong with reading the file.
        } catch(Exception e) {
                System.out.println(e.toString());
                System.exit(1);
        }
        // The completed list of cards after going through every single set.
        return cards;
    }
}