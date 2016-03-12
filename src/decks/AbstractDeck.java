package decks;

import interfaces.ICard;
import interfaces.IDeck;

import java.util.Collections;
import java.util.List;


public abstract class AbstractDeck implements IDeck {

    AbstractDeck(List<ICard> deck, List<ICard> drop){
        this.deck = deck;
        this.drop = drop;
    }

    List<ICard> deck;
    List<ICard> drop;

    @Override
    public void shuffle(){
        for(ICard i : drop){
            deck.add(i);
        }
        drop.clear();
        Collections.shuffle(deck);
    }

    @Override
    public ICard getNextCard(){
        ICard card = deck.get(0);
        deck.remove(card);

        return card;
    }

    @Override
    public void dropCard(ICard card){
        drop.add(0, card);
    }

    @Override
    public List<ICard> getDrop(){
        return drop;
    }

    @Override
    public List<ICard> getDeck() { return deck; }


}
