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
}
