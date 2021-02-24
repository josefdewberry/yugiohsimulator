package yugioh;

/**
 * The properties of a spell or trap card. Unlock a monster type, a spell or trap can
 * have only one property.
 * 
 * @author josefdewberry
 */
public enum Property {

    // The properties with accompanying toString() methods. Some of these are spell-only
    // or trap-only but to put them in one class doesn't matter.
    NORMAL {
        public String toString() { return("Normal"); }
    },
    CONTINUOUS {
        public String toString() { return("Continuous"); }
    },
    EQUIP {
        public String toString() { return("Equip"); }
    },
    FIELD {
        public String toString() { return("Field"); }
    };

    /**
     * Finds the matching property given a string.
     * 
     * @param s The given string.
     * @return The matching property.
     */
    public static Property findMatch(String s) {
        if (s.equals("Normal")) { return Property.NORMAL; }
        else if (s.equals("Continuous")) { return Property.CONTINUOUS; }
        else if (s.equals("Equip")) { return Property.EQUIP; }
        else if (s.equals("Field")) { return Property.FIELD; }
        else { return null; }
    }
}