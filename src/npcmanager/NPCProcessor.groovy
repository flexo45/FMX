package npcmanager

import ai.MainLogic
import cards.Curse
import cards.Monster
import cards.Profession
import cards.Race
import fightmanager.Fight
import gamemanager.CardStackBuffer
import gamemanager.GameProcessor
import gamemanager.Player
import interfaces.ICard
import interfaces.IClass
import interfaces.IRace
import log.Log
import ui.gametable.ActionListManager

class NPCProcessor {

    private static Log logger = new Log(NPCProcessor.class.name)

    static Thread npcTurn

    static volatile boolean WAIT = false

    static Player npc

    private static void waitPlayerActions(){
        logger.info("Do something")
        GameProcessor.instance.view.actionChangedNotify(ActionListManager.OPPONENT_CONTINUE)
        WAIT = true
        while (WAIT){
            npcTurn.sleep(2000)
        }
    }

    static void processFirstRound(){

        npc = GameProcessor.instance.currentPlayer

        if(MainLogic.changeRace()){
            IRace oldR = npc.race
            IRace newR = MainLogic.selectRace()
            npc.race = newR
            logger.info("$npc change race to $newR from $oldR")
        }

        if(MainLogic.changeProfession()){
            IClass oldC = npc.c1ass
            IClass newC = MainLogic.selectClass()
            npc.c1ass = newC
            logger.info("$npc change class to $newC from $oldC")
        }

        ICard card = GameProcessor.instance.game.doors.getNextCard()
        CardStackBuffer.cardList.add(card)
        GameProcessor.instance.view.cardAddedInStack(card, "deck -> ${npc.name}")

        if(card.class.equals(Monster.class)){
            GameProcessor.instance.fighting = new Fight(monster: card as Monster, hero: npc)

            waitPlayerActions()

            Fight.nextBattleRound()

            GameProcessor.instance.view.stackCleared()
        }
        else if(card.class.equals(Curse.class)){

            waitPlayerActions()

            GameProcessor.instance.view.stackCleared()
        }
        else {

            waitPlayerActions()

            npc.hand.add(card)

            GameProcessor.instance.view.stackCleared()
        }
    }

    static void processSecondRound(){

        GameProcessor.instance.view.gameInfoChangedNotify()

        if(GameProcessor.instance.fighting == null){
            //TODO TRY FIGHT

            //OR GET SECOND CARD
            ICard second_card = GameProcessor.instance.game.doors.getNextCard()
            GameProcessor.instance.currentPlayer.hand.add(second_card)
        }

        GameProcessor.instance.view.gameInfoChangedNotify()

        waitPlayerActions()
    }

    static void processThirdRound(){

        GameProcessor.instance.nextTurn()
    }
}
