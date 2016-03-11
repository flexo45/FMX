package ai

import gamemanager.GameProcessor

class MainLogic {
    static boolean changeRace(){
        def result = false
        if(GameProcessor.instance.currentPlayer.race == GameProcessor.instance.game.defaultRace) result = true
        return result
    }

    static boolean changeProfession(){
        def result = false
        if(GameProcessor.instance.currentPlayer.c1ass == GameProcessor.instance.game.defaultClass) result = true
        return result
    }
}
