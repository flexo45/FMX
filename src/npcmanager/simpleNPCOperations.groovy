package npcmanager

import ai.MainLogic
import interfaces.IClass
import interfaces.IRace
import log.Log

class SimpleNPCOperations {

    private static Log logger = new Log(SimpleNPCOperations.class.name)

    public static changeRaceProcess(MainLogic ai){
        if(ai.changeRace()){
            IRace oldR = ai.npc.race
            IRace newR = ai.selectRace()
            ai.npc.race = newR
            logger.info("${ai.npc} change race to $newR from $oldR")
        }
    }

    public static changeClassProcess(MainLogic ai){
        if(ai.changeProfession()){
            IClass oldC = ai.npc.c1ass
            IClass newC = ai.selectClass()
            ai.npc.c1ass = newC
            logger.info("${ai.npc} change class to $newC from $oldC")
        }
    }

    public static checkForImproveYourSelf(MainLogic ai){

        changeRaceProcess(ai)

        changeClassProcess(ai)
    }

}
