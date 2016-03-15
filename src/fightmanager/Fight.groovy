package fightmanager

import cards.Monster
import gamemanager.GameProcessor
import gamemanager.Player
import interfaces.ICard
import log.Log
import ui.gametable.ActionListManager
import dice.Dice

public class Fight {

    private static Log logger = new Log(Fight.class.name)

    Player hero;
    Monster monster;

    int heroBuff = 0;
    int monsterBuff = 0;

    void changeMonsterBuff(int power){
        monsterBuff = monsterBuff + power
    }

    void getObscenity(){
        monster.obscenity(hero)

        logger.info("${hero.name} get obscentity from $monster: ${monster.obscenity} to ${monster.obscenity_value}")

        GameProcessor.instance.view.equipmentChangedNotify()
        GameProcessor.instance.view.gameInfoChangedNotify()
        GameProcessor.instance.view.playerHandChangedNotify()
        GameProcessor.instance.view.playersChangedNotify()
        GameProcessor.instance.view.stackCleared()
        GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT_END)

        closeFight()
    }

    void getSuccess(){
        hero.level += monster.add_level
        logger.info("${hero.name} increase your level on ${monster.add_level}")

        for(int i = 0; i < monster.gold; i++){
            ICard card = GameProcessor.instance.game.golds.getNextCard()
            logger.info("${hero.name} get $card for win")
            hero.hand.add(card)
        }

        GameProcessor.instance.view.gameInfoChangedNotify()
        GameProcessor.instance.view.playerHandChangedNotify()
        GameProcessor.instance.view.playersChangedNotify()
        GameProcessor.instance.view.stackCleared()
        GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT_END)

        closeFight()
    }

    boolean isWin(){
        if(hero.getBaseRating() + heroBuff > monster.level + monsterBuff){
            return true
        }
        else {
            return false
        }
    }

    static void tryRun(){
        int roll = Dice.rollD6()
        if(roll >= 5){
            logger.info("'${GameProcessor.instance.fighting.hero.name}' " +
                    "successful run from '${GameProcessor.instance.fighting.monster.name}'")

            GameProcessor.instance.view.gameInfoChangedNotify()
            GameProcessor.instance.view.playerHandChangedNotify()
            GameProcessor.instance.view.playersChangedNotify()
            GameProcessor.instance.view.stackCleared()
            GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT_END)

            closeFight()
        }
        else{
            logger.info("'${GameProcessor.instance.fighting.hero.name}' " +
                    "faild to run from '${GameProcessor.instance.fighting.monster.name}'")

            GameProcessor.instance.fighting.getObscenity()
        }
    }

    private static void closeFight(){ GameProcessor.instance.fighting = null }

    static void nextBattleRound(){
        //TODO process AI
        if(GameProcessor.instance.fighting.isWin()){
            logger.info("${GameProcessor.instance.fighting.hero.name} is win")
            GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT_WIN)
        }
        else {
            logger.info("${GameProcessor.instance.fighting.hero.name} is lose")
            GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT_FAIL)
        }
    }

}