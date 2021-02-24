package yugioh;

/**
 * The raritys a card can have in a given set. This has no bearing on a duel
 * but will be useful if I implement some sort of card searcher in the future.
 * 
 * @author josefdewberry
 */
public enum Rarity {

    // The raritys, not really in any order. toString() methods will need to be made
    // if these are ever the be printed.
    COMMON,
    RARE,
    SECRETRARE,
    SHORTPRINT,
    SUPERRARE,
    SUPERSHORTPRINT,
    ULTRARARE;

    /**
     * Find the appropriate rarity given a string.
     * 
     * @param s The given string.
     * @return The appropriate rarity.
     */
    public static Rarity findMatch(String s) {
        if (s.equals("Common")) { return Rarity.COMMON; }
        else if (s.equals("Rare")) { return Rarity.RARE; }
        else if (s.equals("Secret Rare")) { return Rarity.SECRETRARE; }
        else if (s.equals("Short Print")) { return Rarity.SHORTPRINT; }
        else if (s.equals("Super Short Print")) { return Rarity.SUPERSHORTPRINT; }
        else if (s.equals("Super Rare")) { return Rarity.SUPERRARE; }
        else if (s.equals("Ultra Rare")) { return Rarity.ULTRARARE; }
        else { return null; }
    }
}