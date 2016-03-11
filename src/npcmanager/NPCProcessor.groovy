package npcmanager

import ai.MainLogic
import cards.Curse
import cards.Monster
import cards.Profession
import cards.Race
import gamemanager.CardStackBuffer
import gamemanager.GameProcessor
import interfaces.ICard
import interfaces.IClass
import interfaces.IRace

/**
 * Created by m.guriev on 11.02.2016.
 */
class NPCProcessor {
    static Thread npcTurn

    static volatile boolean WAIT = false

    private static void waitPlayerActions(){
        GameProcessor.instance.view.actionChangedNotify()
        WAIT = true
        while (WAIT){
            npcTurn.sleep(2000)
        }
    }

    static void processFirstRound(){

        IRace race = (IRace)GameProcessor.instance.currentPlayer.hand.find{ it.class.equals(Race.class)}
        if(race != null){
            if(MainLogic.changeRace()) GameProcessor.instance.currentPlayer.race = race
            GameProcessor.instance.view.playersChangedNotify()
        }

        IClass profession = (IClass)GameProcessor.instance.currentPlayer.hand.find{ it.class.equals(Profession.class)}
        if(profession != null){
            if(MainLogic.changeProfession()) GameProcessor.instance.currentPlayer.c1ass = profession
            GameProcessor.instance.view.playersChangedNotify()
        }

        waitPlayerActions()

        ICard card = GameProcessor.instance.game.doors.getNextCard()
        CardStackBuffer.cardList.add(card)
        GameProcessor.instance.view.cardAddedInStack(card, "deck -> ${GameProcessor.instance.currentPlayer}")

        if(card.class.equals(Monster.class)){
            GameProcessor.instance.fight = true //FIGHT BEGIN

            waitPlayerActions()

            GameProcessor.instance.view.stackCleared()
        }
        else if(card.class.equals(Curse.class)){
            GameProcessor.instance.curse = true //CURSE BEGIN

            waitPlayerActions()

            GameProcessor.instance.view.stackCleared()
        }
        else {
            GameProcessor.instance.part = 2 //ROUND 1 PART 2 BEGIN

            waitPlayerActions()

            GameProcessor.instance.currentPlayer.hand.add(card)

            GameProcessor.instance.view.stackCleared()
        }
    }

    static void processSecondRound(){

        GameProcessor.instance.part = 1 //ROUND 1 PART 2 END
        GameProcessor.instance.round = 2 //ROUND 2 BEGIN

        GameProcessor.instance.view.gameInfoChangedNotify()

        if(!GameProcessor.instance.fight){
            //TODO TRY FIGHT

            //OR GET SECOND CARD
            ICard second_card = GameProcessor.instance.game.doors.getNextCard()
            GameProcessor.instance.currentPlayer.hand.add(second_card)
        }

        GameProcessor.instance.view.gameInfoChangedNotify()

        waitPlayerActions()
    }

    static void processThirdRound(){
        GameProcessor.instance.round = 3 //ROUND 2 END

        GameProcessor.instance.nextTurn()
    }
}
