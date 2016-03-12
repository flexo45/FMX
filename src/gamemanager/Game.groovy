package gamemanager

import interfaces.IClass
import interfaces.IDeck
import interfaces.IRace

class Game {
    List<Player> playerList = []
    Integer turn = 1
    IDeck doors
    IDeck golds
    IRace defaultRace
    IClass defaultClass
    String gameName

    @Override
    public String toString(){
        return "game[gameName:$gameName, playerList:$playerList, turn:$turn, doors_deck:$doors" +
                ", golds_deck:$golds, defaultRace:$defaultRace, defaultClass:$defaultClass]"
    }
}
