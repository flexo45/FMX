package npcmanager
/**
 * Created by m.guriev on 11.02.2016.
 */
class NPCTurn implements Runnable {
    @Override
    void run() {

        NPCProcessor.processFirstRound()
        NPCProcessor.processSecondRound()
        NPCProcessor.processThirdRound()
    }
}
