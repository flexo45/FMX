package interfaces;

import java.util.List;

public interface IDeck {
    void shuffle();
    ICard getNextCard();
    void dropCard(ICard card);
    List<ICard> getDrop();
    List<ICard> getDeck();
}
