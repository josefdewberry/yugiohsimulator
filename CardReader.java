package yugioh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.io.File;

import yugioh.Card.*;
import yugioh.CardType;

public class CardReader {

    public static Card buildMonster(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("\t");

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

        scanner.close();

        Scanner typeScanner = new Scanner(type);
        typeScanner.useDelimiter("/");
        
        ArrayList<Type> tempTypes = new ArrayList();
        while (typeScanner.hasNext()) {
            tempTypes.add(Type.findMatch(typeScanner.next()));
        }

        typeScanner.close();

        Type[] types = new Type[tempTypes.size()];
        
        for (int i = 0; i < tempTypes.size(); i++) {
            types[i] = tempTypes.get(i);
        }

        Card card = new MonsterCard(name, cardType, text, number, code, rarity, attribute,
                                    level, types, atk, def);
        return card;
    }

    public static Card buildBackrow(String s) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("\t");

        CardType cardType = CardType.findMatch(scanner.next());
        String name = scanner.next();
        String text = scanner.next();
        Property property = Property.findMatch(scanner.next());
        scanner.next();
        scanner.next();
        scanner.next();
        scanner.next();
        int code = Integer.parseInt(scanner.next());
        String number = scanner.next();
        Rarity rarity = Rarity.findMatch(scanner.next());

        scanner.close();
    
        Card card = new BackrowCard(name, cardType, text, number, code, rarity, property);
        return card;
    }

    public static void readCard(String s, ArrayList cards) {
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("\t");
        CardType cardType = CardType.findMatch(scanner.next());

        if (cardType == CardType.MONSTER) {
            cards.add(buildMonster(s));
        } else if (cardType == CardType.SPELL || cardType == CardType.TRAP) {
            cards.add(buildBackrow(s));
        }

        scanner.close();
    }

    public static ArrayList<Card> readCards() {
        ArrayList cards = new ArrayList();

        try {
            
            File f = new File("./yugioh/Set Lists");

            for (File fileEntry : f.listFiles()) {
                File file = new File("./yugioh/Set Lists/" + fileEntry.getName());
                Scanner fileScanner = new Scanner(file);
                fileScanner.nextLine();
                while (fileScanner.hasNextLine()) {
                    readCard(fileScanner.nextLine(), cards);
                }

                fileScanner.close();
            } 

        } catch(Exception e) {
                System.out.println(e.toString());
                System.exit(0);
        }

        return cards;

    }

}