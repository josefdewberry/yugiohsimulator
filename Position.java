package yugioh;

public enum Position {
    ATK,
    UPDEF,
    SET;

    public static String toString(Position p) {
        if (p == ATK) return " in attack position.";
        else if (p == UPDEF) return " in face-up defense position.";
        else if (p == SET) return " in face-down defense position.";
        else return null;
    }

    public static Position findMatch(String s) {
        if (s.equalsIgnoreCase("atk")) return ATK;
        else if (s.equalsIgnoreCase("def")) return SET;
        else return null;
    }
}