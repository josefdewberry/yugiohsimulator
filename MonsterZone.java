package yugioh;

import yugioh.Card.*;
import yugioh.Position;

public class MonsterZone {
    public MonsterCard card;
    public Position position;
    boolean switched;
    boolean attacked;

    public MonsterZone(Card card, Position position) {
        this.card = (MonsterCard) card;
        this.position = position;
        attacked = false;
        switched = true;
    }
}