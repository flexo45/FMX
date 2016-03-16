package npcmanager

import ai.MainLogic

class NPCTurn implements Runnable {

    MainLogic ai

    @Override
    void run() {

        NPCProcessor.processFirstRound(ai)
        NPCProcessor.processSecondRound(ai)
        NPCProcessor.processThirdRound(ai)
    }
}
