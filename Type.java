package yugioh;

/**
 * An enum for all the "types" in yugioh, which includes monster types and the effects
 * they can have. The usage of these types will be modern, meaning all types are shown
 * such as Flip and Effect, instead of just Effect.
 * 
 * @author josefdewberry
 */
public enum Type {

    // Set up the enums and also give them each a toString() method.
    AQUA {
        public String toString() { return("Aqua"); }
    },
    BEAST {
        public String toString() { return("Beast"); }
    },
    BEASTWARRIOR {
        public String toString() { return("Beast-Warrior"); }
    },
    DINOSAUR {
        public String toString() { return("Dinosaur"); }
    },
    DRAGON {
        public String toString() { return("Dragon"); }
    },
    FAIRY {
        public String toString() { return("Fairy"); }
    },
    FIEND {
        public String toString() { return("Fiend"); }
    },
    FISH {
        public String toString() { return("Fish"); }
    },
    INSECT {
        public String toString() { return("Insect"); }
    },
    MACHINE {
        public String toString() { return("Machine"); }
    },
    PLANT {
        public String toString() { return("Plant"); }
    },
    PYRO {
        public String toString() { return("Pyro"); }
    },
    REPTILE {
        public String toString() { return("Reptile"); }
    },
    ROCK {
        public String toString() { return("Rock"); }
    },
    SEASERPENT {
        public String toString() { return("Sea Serpent"); }
    },
    SPELLCASTER {
        public String toString() { return("Spellcaster"); }
    },
    THUNDER {
        public String toString() { return("Thunder"); }
    },
    WARRIOR {
        public String toString() { return("Warrior"); }
    },
    WINGEDBEAST {
        public String toString() { return("Winged Beast"); }
    },
    ZOMBIE {
        public String toString() { return("Zombie"); }
    },
    NORMAL {
        public String toString() { return("Normal"); }
    },
    EFFECT {
        public String toString() { return("Effect"); }
    },
    FUSION {
        public String toString() { return("Fusion"); }
    },
    FLIP {
        public String toString() { return("Flip"); }
    };

    /**
     * Find a matching enum for a string. Despite ignoring case, some are specific such
     * as beast-warrior containing a dash and others being two words.
     * 
     * @param s The enum in string format.
     * @return The matching type as an enum.
     */
    public static Type findMatch(String s) {
        if (s.equalsIgnoreCase("aqua")) { return Type.AQUA; }
        else if (s.equalsIgnoreCase("beast")) { return Type.BEAST; }
        else if (s.equalsIgnoreCase("beast-warrior")) { return Type.BEASTWARRIOR; }
        else if (s.equalsIgnoreCase("dinosaur")) { return Type.DINOSAUR; }
        else if (s.equalsIgnoreCase("dragon")) { return Type.DRAGON; }
        else if (s.equalsIgnoreCase("fairy")) { return Type.FAIRY; }
        else if (s.equalsIgnoreCase("fiend")) { return Type.FIEND; }
        else if (s.equalsIgnoreCase("fish")) { return Type.FISH; }
        else if (s.equalsIgnoreCase("insect")) { return Type.INSECT; }
        else if (s.equalsIgnoreCase("machine")) { return Type.MACHINE; }
        else if (s.equalsIgnoreCase("plant")) { return Type.PLANT; }
        else if (s.equalsIgnoreCase("pyro")) { return Type.PYRO; }
        else if (s.equalsIgnoreCase("reptile")) { return Type.REPTILE; }
        else if (s.equalsIgnoreCase("rock")) { return Type.ROCK; }
        else if (s.equalsIgnoreCase("sea Serpent")) { return Type.SEASERPENT; }
        else if (s.equalsIgnoreCase("spellcaster")) { return Type.SPELLCASTER; }
        else if (s.equalsIgnoreCase("thunder")) { return Type.THUNDER; }
        else if (s.equalsIgnoreCase("warrior")) { return Type.WARRIOR; }
        else if (s.equalsIgnoreCase("winged beast")) { return Type.WINGEDBEAST; }
        else if (s.equalsIgnoreCase("zombie")) { return Type.ZOMBIE; }
        else if (s.equalsIgnoreCase("normal")) { return Type.NORMAL; }
        else if (s.equalsIgnoreCase("effect")) { return Type.EFFECT; }
        else if (s.equalsIgnoreCase("fusion")) { return Type.FUSION; }
        else if (s.equalsIgnoreCase("flip")) { return Type.FLIP; }
        else { return null; }

    }
}