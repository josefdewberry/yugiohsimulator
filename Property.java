package yugioh;

public enum Property {
    NORMAL {
        public String toString() {
            return("Normal");
        }
    },
    CONTINUOUS {
        public String toString() {
            return("Continuous");
        }
    },
    EQUIP {
        public String toString() {
            return("Equip");
        }
    },
    FIELD {
        public String toString() {
            return("Field");
        }
    };

    public static Property findMatch(String s) {
        if (s.equals("Normal")) { return Property.NORMAL; }
        else if (s.equals("Continuous")) { return Property.CONTINUOUS; }
        else if (s.equals("Equip")) { return Property.EQUIP; }
        else if (s.equals("Field")) { return Property.FIELD; }
        else { return null; }
    }
}