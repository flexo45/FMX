package npcmanager

import ai.MainLogic
import cards.Curse
import cards.Monster
import fightmanager.Fight
import gamemanager.CardStackBuffer
import gamemanager.GameProcessor
import gamemanager.Player
import interfaces.ICard
import log.Log
import ui.gametable.ActionListManager

class NPCProcessor {

    private static Log logger = new Log(NPCProcessor.class.name)

    static Thread npcTurn

    static volatile boolean WAIT = false

    private static void waitPlayerActions(){
        logger.info("Do something")
        GameProcessor.instance.view.actionChangedNotify(ActionListManager.OPPONENT_CONTINUE)
        WAIT = true
        while (WAIT){
            npcTurn.sleep(2000)
        }
    }

    private static void waitPlayerActions(int situation){
        logger.info("Do something")
        GameProcessor.instance.view.actionChangedNotify(situation)
        WAIT = true
        while (WAIT){
            npcTurn.sleep(2000)
        }
    }

    static void processFirstRound(MainLogic ai) throws Exception{

        Player npc = ai.npc

        SimpleNPCOperations.checkForImproveYourSelf(ai)

        GameProcessor.instance.view.refreshView()

        if(ai.getFirstDoorCard()){
            ICard card = GameProcessor.instance.game.doors.getNextCard()
            CardStackBuffer.cardList.add(card)
            GameProcessor.instance.view.cardAddedInStack(card, "deck -> ${npc.name}")

            if(card.class.equals(Monster.class)){
                GameProcessor.instance.fighting = new Fight(monster: card as Monster, hero: npc)

                while (GameProcessor.instance.fighting != null){

                    Fight.nextBattleRoundNpc()

                    waitPlayerActions(ActionListManager.OPPONENT_FIGHT)

                }

                GameProcessor.instance.view.stackCleared()
            }
            else if(card.class.equals(Curse.class)){

                waitPlayerActions()

                (card as Curse).execute(npc)

                logger.info("${npc.name} get curse ${card.name}")

                GameProcessor.instance.view.stackCleared()
            }
            else {

                waitPlayerActions()

                npc.hand.add(card)

                logger.info("${npc.name} get door ${card.name}")

                GameProcessor.instance.view.stackCleared()
            }
        }


    }

    static void processSecondRound(MainLogic ai) throws Exception{

        SimpleNPCOperations.checkForImproveYourSelf(ai)

        GameProcessor.instance.view.refreshView()

        if(GameProcessor.instance.fighting == null){
            //TODO TRY FIGHT

            //OR GET SECOND CARD
            ICard second_card = GameProcessor.instance.game.doors.getNextCard()
            GameProcessor.instance.currentPlayer.hand.add(second_card)
        }

        GameProcessor.instance.view.refreshView()

        waitPlayerActions()
    }

    static void processThirdRound(MainLogic ai) throws Exception{

        GameProcessor.instance.nextTurn()
    }
}
