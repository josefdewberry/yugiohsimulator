package yugioh;

/**
 * The positions a monster can have when played.
 * 
 * @author josefdewberry
 */
public enum Position {

    // The positions. The toString() methods are used for checking the field. 
    ATK {
        public String toString() { return " in attack position."; }
    },
    // Defense monsters can be both face-up or face-down (set).
    UPDEF {
        public String toString() { return " in face-up defense position."; }
    },
    SET {
        public String toString() { return " in face-down defense position."; }
    };

    /**
     * Find a matching position given a string.
     * 
     * @param s The given string.
     * @return The matching position.
     */
    public static Position findMatch(String s) {
        if (s.equalsIgnoreCase("atk")) return ATK;
        else if (s.equalsIgnoreCase("def")) return SET;
        else return null;
    }
}