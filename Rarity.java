package yugioh;

public enum Rarity {
    COMMON,
    RARE,
    SECRETRARE,
    SHORTPRINT,
    SUPERRARE,
    SUPERSHORTPRINT,
    ULTRARARE;

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