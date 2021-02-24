package yugioh;

import java.io.*;

import yugioh.Attribute;
import yugioh.CardType;
import yugioh.Property;
import yugioh.Type;

/**
 * A card class which represents a standard yugioh card.
 * 
 * @author josefdewberry
 */
public class Card implements Serializable {
    
    // Despite monster cards and spell/trap cards having very different properties, these
    // are shared by all cards.
    private String name;
    private CardType cardType;
    private String text;
    private String number;
    private int code;
    private Rarity rarity;

    /**
     * Card constructor.
     * 
     * @param name The name of the card.
     * @param cardType The card type (spell/trap/monster).
     * @param text The text in the card description box (flavor or effect).
     * @param number The card number in the set (LOB-EN006 means it was the 6th card released
     * in the Legend Of Blue-eyes set.)
     * @param code The unique card code in the bottom-left corner of the card.
     * @param rarity The rarity of the card, unique to the card's release in each set.
     */
    public Card(String name, CardType cardType, String text, String number,
                int code, Rarity rarity) {
        this.name = name;
        this.cardType = cardType;
        this.text = text;
        this.number = number;
        this.code = code;
        this.rarity = rarity;
    }

    /**
     * Name getter.
     * 
     * @return The card name.
     */
    public String getName() {
        return name;
    }

    /**
     * CardType getter.
     * 
     * @return The card type.
     */
    public CardType getCardType() {
        return cardType;
    }

    /**
     * Text getter.
     * 
     * @return The card text.
     */
    public String getText() {
        return text;
    }

    /**
     * Number getter.
     * 
     * @return The card number.
     */
    private String getNumber() {
        return number;
    }

    /**
     * Code getter.
     * 
     * @return The card code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Rarity getter.
     * 
     * @return The rarity.
     */
    public Rarity getRarity() {
        return rarity;
    }

    /**
     * Returns a detailed one line string of a card, for displaying in a player's hand
     * or on the field.
     * 
     * @param c The card to turn into a string.
     * @return The card as a string.
     */
    public static String detailedString(Card card) {
        if (card.getCardType() == CardType.MONSTER) {
            MonsterCard c = (MonsterCard) card;
            return "[" + c.getCardType() + "] " + c.getName() + " - " + c.getAtk() + "/" 
                   + c.getDef() + " - " + c.getAttribute() + " Level " + c.getLevel() + " " 
                   + c.getType();
        } else {
            BackrowCard c = (BackrowCard) card;
            return "[" + c.getCardType() + "] " + c.getName() + " - " + c.getProperty();
        }
    }

    /**
     * A sub-class of a yugioh card, a monster card.
     * 
     * @author josefdewberry
     */
    public static class MonsterCard extends Card {

        // Properties unique to monster cards.
        private Attribute attribute;
        private int level;
        private Type[] type;
        private int atk;
        private int def;
    
        /**
         * Monster card constructor.
         * 
         * @param name The name of the card.
         * @param cardType The card type (must be monster).
         * @param text The text in the card description box (flavor or effect).
         * @param number The card number in the set (LOB-EN006 means it was the 6th card released
         * in the Legend Of Blue-eyes set.)
         * @param code The unique card code in the bottom-left corner of the card.
         * @param rarity The rarity of the card, unique to the card's release in each set.
         * @param attribute The monster's attribute, shown in the top right of the card.
         * @param level The monster's level (the number of stars under the name).
         * @param type The monster's types (the bold words in the card description box).
         * @param atk The monster's attack value (at the bottom).
         * @param def The monster's defense value (at the bottom).
         */
        public MonsterCard(String name, CardType cardType, String text, String number,
                           int code, Rarity rarity, Attribute attribute, int level,
                           Type[] type, int atk, int def) {
            super(name, cardType, text, number, code, rarity);
            this.attribute = attribute;
            this.level = level;
            this.type = type;
            this.atk = atk;
            this.def = def;            
        }

        /**
         * Level getter.
         * 
         * @return The level.
         */        
        public int getLevel() {
            return level;
        }

        /**
         * Attack getter.
         * 
         * @return The attack value.
         */   
        public int getAtk() {
            return atk;
        }

        /**
         * Defense getter.
         * 
         * @return The defense value.
         */   
        public int getDef() {
            return def;
        }

        /**
         * Attribute getter.
         * 
         * @return The attribute.
         */   
        public Attribute getAttribute() {
            return attribute;
        }

        /**
         * Type getter.
         * 
         * @return The types value.
         */   
        public String getType() {
            String s = "";
            for (Type t : type) {
                s += t.toString() + "/";
            }
            s = s.substring(0, s.length() - 1);
            return s;
        }
        
        /**
         * Converts the card to a string with all of it's properties pertinent to a duel
         * (things like the cards code and rarity will never come up in a duel.)
         * 
         * @return The card as a string.
         */
        public String toString() {
            String s = "";
            s += getCardType() + "\n";
            s += getName() + "\n";
            s += attribute + "\n";
            for (Type t : type) {
                s += t + "/";
            }
            s = s.substring(0, s.length() - 1);
            s += "\n";
            s += getText() + "\n";
            s += atk + "\n";
            s += def + "\n";
    
            return s;
        }
    }

    /**
     * A spell or trap card. We can combine them because they only have one proprerty to
     * worry about and they often share it.
     * 
     * @author josefdewberry.
     */
    public static class BackrowCard extends Card {

        // The only property unique to spell and trap cards.
        private Property property;
    
        /**
         * A backrow card constructor.
         * 
         * @param name The name of the card.
         * @param cardType The card type (spell/trap).
         * @param text The text in the card description box (flavor or effect).
         * @param number The card number in the set (LOB-EN006 means it was the 6th card released
         * in the Legend Of Blue-eyes set.)
         * @param code The unique card code in the bottom-left corner of the card.
         * @param rarity The rarity of the card, unique to the card's release in each set.
         * @param property The spell/trap card's property.
         */
        public BackrowCard(String name, CardType cardType, String text, String number,
                             int code, Rarity rarity, Property property) {
            super(name, cardType, text, number, code, rarity);
            this.property = property;
        }
    
        /**
         * Property getter.
         * 
         * @return The Property.
         */   
        public Property getProperty() {
            return property;
        }

        /**
         * Turns the spell/trap card to a string.
         * 
         * @return The spell/trap card as a string.
         */
        public String toString() {
            String s = "";
            s += getCardType() + "\n";
            s += getName() + "\n";
            s += property + "\n";
            s += getText() + "\n";
    
            return s;
        }
    }
}