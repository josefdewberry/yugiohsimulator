package yugioh;

/**
 * The types of actions a player can take during a duel.
 * 
 * @author josefdewberry
 */
public enum Action {

    // The actions, in no particular order.
    TURNSTART,
    ATTACK,
    BATTLEPHASE,
    CHECKFIELD,
    CHECKGY,
    CHECKOPPGY,
    CHECKHAND,
    MAINPHASE,
    NORMALSUMMON,
    SETMONSTER,
    SWITCHPOSITION,
    TRIBUTESUMMON,
    ENDTURN;

    /**
     * Finds the matching action given a string. Some have multiple strings that will work.
     * 
     * @param s The given string.
     * @return The matching action.
     */
    public static Action findMatch(String s) {
        if (s.equalsIgnoreCase("end turn")) return ENDTURN;
        else if (s.equalsIgnoreCase("end phase")) return ENDTURN;
        else if (s.equalsIgnoreCase("normal summon")) return NORMALSUMMON;
        else if (s.equalsIgnoreCase("check field")) return CHECKFIELD;
        else if (s.equalsIgnoreCase("check hand")) return CHECKHAND;
        else if (s.equalsIgnoreCase("check graveyard")) return CHECKGY;
        else if (s.equalsIgnoreCase("check opponent's graveyard")) return CHECKOPPGY;
        else if (s.equalsIgnoreCase("check opponents graveyard")) return CHECKOPPGY;
        else if (s.equalsIgnoreCase("tribute summon")) return TRIBUTESUMMON;
        else if (s.equalsIgnoreCase("set monster")) return SETMONSTER;
        else if (s.equalsIgnoreCase("set")) return SETMONSTER;
        else if (s.equalsIgnoreCase("attack")) return ATTACK;
        else if (s.equalsIgnoreCase("enter battle phase")) return BATTLEPHASE;
        else if (s.equalsIgnoreCase("battle phase")) return BATTLEPHASE;
        else if (s.equalsIgnoreCase("switch position")) return SWITCHPOSITION;
        else if (s.equalsIgnoreCase("switch monster")) return SWITCHPOSITION;
        else if (s.equalsIgnoreCase("enter main phase")) return MAINPHASE;
        else if (s.equalsIgnoreCase("main phase")) return MAINPHASE;
        else return null;
    }
}