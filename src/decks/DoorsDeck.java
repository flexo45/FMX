package decks;

import interfaces.ICard;
import interfaces.IDeck;

import java.util.List;

public class DoorsDeck extends AbstractDeck implements IDeck {
    public DoorsDeck(List<ICard> deck, List<ICard> drop) { super(deck, drop); }

    @Override
    public String toString(){
        return "doorsDeck[deck:" + super.deck + ", drop:" + super.drop + "]";
    }
}
