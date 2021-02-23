package yugioh;

import java.io.*;

import yugioh.Attribute;
import yugioh.CardType;
import yugioh.Property;
import yugioh.Type;

public class Card implements Serializable {
    
    private String name;
    private CardType cardType;
    private String text;
    private String number;
    private int code;
    private Rarity rarity;

    public Card(String name, CardType cardType, String text, String number,
                int code, Rarity rarity) {
        this.name = name;
        this.cardType = cardType;
        this.text = text;
        this.number = number;
        this.code = code;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getText() {
        return text;
    }

    private String getNumber() {
        return number;
    }

    public int getCode() {
        return code;
    }

    private Rarity getRarity() {
        return rarity;
    }

    public static class MonsterCard extends Card {

        private Attribute attribute;
        private int level;
        private Type[] type;
        private int atk;
        private int def;
    
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
        
        public int getLevel() {
            return level;
        }

        public int getAtk() {
            return atk;
        }

        public int getDef() {
            return def;
        }
        
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

    public static class BackrowCard extends Card {

        private Property property;
    
        public BackrowCard(String name, CardType cardType, String text, String number,
                             int code, Rarity rarity, Property property) {
            super(name, cardType, text, number, code, rarity);
            this.property = property;
        }
    
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