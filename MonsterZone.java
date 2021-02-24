package yugioh;

import yugioh.Card.*;
import yugioh.Position;

/**
 * A monster zone on the field, 5 for each player, 10 total.
 * 
 * @author josefdewberry
 */
public class MonsterZone {
    
    // A zone can have a card or be empty.
    public MonsterCard card;
    // Monsters have 3 position they can be in.
    public Position position;
    // When a monster is played it's important to remember if it's attacked, switched position,
    // or even if they were summoned this turn.
    boolean switched;
    boolean attacked;
    boolean summoned;

    /**
     * MonsterZone constructor.
     * 
     * @param card The card inhabiting the zone. We'll trust that it is a monster.
     * @param position The position of the monster in the zone.
     */
    public MonsterZone(Card card, Position position) {
        this.card = (MonsterCard) card;
        this.position = position;
        attacked = false;
        switched = false;
        summoned = true;
    }
}