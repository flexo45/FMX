package fightmanager

import cards.Monster
import gamemanager.GameProcessor
import gamemanager.Player
import ui.gametable.ActionListManager

public class Fight {

    Player hero;
    Monster monster;

    int heroBuff = 0;
    int monsterBuff = 0;

    void getObscenity(){
        monster.obscenity()

        GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT_END)
    }

    void getSuccess(){
        for(int i = 1; i < monster.gold; i++){
            hero.hand.add(GameProcessor.instance.game.golds.getNextCard())
        }

        GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT_END)
    }

    boolean isWin(){
        if(hero.level + heroBuff > monster.level + monsterBuff){
            return true
        }
        else {
            return false
        }
    }

    static void nextBattleRound(){
        if(GameProcessor.instance.fighting.isWin()){
            GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT_WIN)
        }
        else {
            GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT_FAIL)
        }
    }

}