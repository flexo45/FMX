package npcmanager

class NPCTurn implements Runnable {
    @Override
    void run() {

        NPCProcessor.processFirstRound()
        NPCProcessor.processSecondRound()
        NPCProcessor.processThirdRound()
    }
}
