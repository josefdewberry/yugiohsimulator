package yugioh;

public enum Attribute {
    DARK,
    LIGHT,
    EARTH,
    FIRE,
    WIND,
    WATER;

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