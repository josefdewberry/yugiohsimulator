package yugioh;

public enum Type {
    AQUA {
        public String toString() {
            return("Aqua");
        }
    },
    BEAST {
        public String toString() {
            return("Beast");
        }
    },
    BEASTWARRIOR {
        public String toString() {
            return("Beast-Warrior");
        }
    },
    DINOSAUR {
        public String toString() {
            return("Dinosaur");
        }
    },
    DRAGON {
        public String toString() {
            return("Dragon");
        }
    },
    FAIRY {
        public String toString() {
            return("Fairy");
        }
    },
    FIEND {
        public String toString() {
            return("Fiend");
        }
    },
    FISH {
        public String toString() {
            return("Fish");
        }
    },
    INSECT {
        public String toString() {
            return("Insect");
        }
    },
    MACHINE {
        public String toString() {
            return("Machine");
        }
    },
    PLANT {
        public String toString() {
            return("Plant");
        }
    },
    PYRO {
        public String toString() {
            return("Pyro");
        }
    },
    REPTILE {
        public String toString() {
            return("Reptile");
        }
    },
    ROCK {
        public String toString() {
            return("Rock");
        }
    },
    SEASERPENT {
        public String toString() {
            return("Sea Serpent");
        }
    },
    SPELLCASTER {
        public String toString() {
            return("Spellcaster");
        }
    },
    THUNDER {
        public String toString() {
            return("Thunder");
        }
    },
    WARRIOR {
        public String toString() {
            return("Warrior");
        }
    },
    WINGEDBEAST {
        public String toString() {
            return("Winged Beast");
        }
    },
    ZOMBIE {
        public String toString() {
            return("Zombie");
        }
    },
    NORMAL {
        public String toString() {
            return("Normal");
        }
    },
    EFFECT {
        public String toString() {
            return("Effect");
        }
    },
    FUSION {
        public String toString() {
            return("Fusion");
        }
    },
    FLIP {
        public String toString() {
            return("Flip");
        }
    };

    public static Type findMatch(String s) {
        if (s.equals("Aqua")) { return Type.AQUA; }
        else if (s.equals("Beast")) { return Type.BEAST; }
        else if (s.equals("Beast-Warrior")) { return Type.BEASTWARRIOR; }
        else if (s.equals("Dinosaur")) { return Type.DINOSAUR; }
        else if (s.equals("Dragon")) { return Type.DRAGON; }
        else if (s.equals("Fairy")) { return Type.FAIRY; }
        else if (s.equals("Fiend")) { return Type.FIEND; }
        else if (s.equals("Fish")) { return Type.FISH; }
        else if (s.equals("Insect")) { return Type.INSECT; }
        else if (s.equals("Machine")) { return Type.MACHINE; }
        else if (s.equals("Plant")) { return Type.PLANT; }
        else if (s.equals("Pyro")) { return Type.PYRO; }
        else if (s.equals("Reptile")) { return Type.REPTILE; }
        else if (s.equals("Rock")) { return Type.ROCK; }
        else if (s.equals("Sea Serpent")) { return Type.SEASERPENT; }
        else if (s.equals("Spellcaster")) { return Type.SPELLCASTER; }
        else if (s.equals("Thunder")) { return Type.THUNDER; }
        else if (s.equals("Warrior")) { return Type.WARRIOR; }
        else if (s.equals("Winged Beast")) { return Type.WINGEDBEAST; }
        else if (s.equals("Zombie")) { return Type.ZOMBIE; }
        else if (s.equals("Normal")) { return Type.NORMAL; }
        else if (s.equals("Effect")) { return Type.EFFECT; }
        else if (s.equals("Fusion")) { return Type.FUSION; }
        else if (s.equals("Flip")) { return Type.FLIP; }
        else { return null; }

    }
}