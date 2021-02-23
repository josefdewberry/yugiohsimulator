package yugioh;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import yugioh.Card;
import yugioh.Player;

public class PlayerBuilder {
    
    public static ArrayList<Card> buildDeck(File f) {
        try {

            Scanner scanner = new Scanner(f);
            scanner.nextLine();
            scanner.nextLine();

            ArrayList<Card> cards = CardReader.readCards();

            ArrayList<Card> deck = new ArrayList();
            
            while (scanner.hasNextLine()) {

                String tempCode = scanner.nextLine();
                if (tempCode.equals("#extra")) {
                    break;
                }
                
                int code = Integer.parseInt(tempCode);
                for (int i = 0; i < cards.size(); i++) {
                    if (cards.get(i).getCode() == code) {
                        deck.add(cards.get(i));
                        break;
                    }
                }

            }

            return deck;

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static ArrayList<Card> buildExtraDeck(File f) {
        try {

            Scanner scanner = new Scanner(f);

            ArrayList<Card> cards = CardReader.readCards();

            ArrayList<Card> extraDeck = new ArrayList();
            
            while (scanner.hasNextLine()) {

                String tempCode = scanner.nextLine();
                if (tempCode.equals("#extra")) {
                    tempCode = scanner.nextLine();
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

            }

            return extraDeck;

        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
}