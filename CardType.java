package yugioh;

/**
 * Cards only have 3 types, which are given here.
 * 
 * @author josefdewberry
 */
public enum CardType {

    // The 3 types.
    MONSTER,
    SPELL,
    TRAP;

    /**
     * Finds the appropriate card type given a string.
     * 
     * @param s The given string.
     * @return The matching card type.
     */
    public static CardType findMatch(String s) {
        if (s.equals("Monster")) { return CardType.MONSTER; } 
        else if (s.equals("Spell")) { return CardType.SPELL; } 
        else if (s.equals("Trap")) { return CardType.TRAP; } 
        else { return null; }
    }
}