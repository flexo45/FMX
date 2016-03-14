package ui.gametable

import interfaces.ICard

public interface GameTableEvents {
    void playerHandChangedNotify()

    void gameInfoChangedNotify()

    void actionChangedNotify(int situation)

    void playersChangedNotify()

    void cardAddedInStack(ICard card, String direction)

    void equipmentChangedNotify()

    void stackCleared()
}