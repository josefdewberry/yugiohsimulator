package yugioh;

/**
 * An enum for the 6 current attributes in the game.
 * 
 * @author josefdewberry
 */
public enum Attribute {
    
    // The attributes. They don't need a toString() method because the printing of attributes
    // happens to follow the proper capitalzation of enums, all caps.
    DARK,
    LIGHT,
    EARTH,
    FIRE,
    WIND,
    WATER;

    /**
     * Find a matching attribute given a string.
     * 
     * @param s The given string.
     * @return The matching attribute.
     */
    public static Attribute findMatch(String s) {
        if (s.equals("Dark")) { return Attribute.DARK; }
        else if (s.equals("Light")) { return Attribute.LIGHT; }
        else if (s.equals("Earth")) { return Attribute.EARTH; }
        else if (s.equals("Fire")) { return Attribute.FIRE; }
        else if (s.equals("Wind")) { return Attribute.WIND; }
        else if (s.equals("Water")) { return Attribute.WATER; }
        else { return null; }
    }
}