package yugioh;

public enum CardType {
    MONSTER,
    SPELL,
    TRAP;

    public static CardType findMatch(String s) {
        if (s.equals("Monster")) { return CardType.MONSTER; } 
        else if (s.equals("Spell")) { return CardType.SPELL; } 
        else if (s.equals("Trap")) { return CardType.TRAP; } 
        else { return null; }
    }
}