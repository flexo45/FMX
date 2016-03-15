package ai

import cards.Profession
import cards.Race
import gamemanager.GameProcessor
import gamemanager.Player
import interfaces.IClass
import interfaces.IRace
import log.Log

class MainLogic {

    private static Log logger = new Log(MainLogic.class.name)

    static boolean changeRace(){
        Player player = GameProcessor.instance.currentPlayer
        def result = false
        IRace race = (IRace)player.hand.find{ it.class.equals(Race.class)}
        if(race != null){
            if(player.race == GameProcessor.instance.game.defaultRace) result = true
            return result
        }
        return result
    }

    static boolean changeProfession(){
        def result = false
        IClass profession = (IClass)GameProcessor.instance.currentPlayer.hand.find{ it.class.equals(Profession.class)}
        if(profession != null){
            if(GameProcessor.instance.currentPlayer.c1ass == GameProcessor.instance.game.defaultClass) result = true
            return result
        }
        return result
    }

    static IRace selectRace(){
        Player player = GameProcessor.instance.currentPlayer
        IRace race = (IRace)player.hand.find{ it.class.equals(Race.class)}
        return race
    }

    static IClass selectClass(){
        Player player = GameProcessor.instance.currentPlayer
        IClass c1ass = (IRace)player.hand.find{ it.class.equals(Profession.class)}
        return c1ass
    }
}
