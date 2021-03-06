package decks;

import interfaces.ICard;
import interfaces.IDeck;

import java.util.List;

public class GoldsDeck extends AbstractDeck implements IDeck {
    public GoldsDeck(List<ICard> deck, List<ICard> drop) { super(deck, drop); }

    @Override
    public String toString(){
        return "doorsDeck[deck:" + super.deck + ", drop:" + super.drop + "]";
    }
}
