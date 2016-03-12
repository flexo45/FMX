package gamemanager

import gamedata.GameDataManager
import fightmanager.Fight
import npcmanager.NPCProcessor
import ui.gametable.ActionListManager

class ActionProcessor {
    static void processActions(String action){
        switch (action){
            case ActionListManager.SAVE:
                GameDataManager.instance.saveGame(GameProcessor.instance.game)
                break
            case ActionListManager.OPEN_DOOR:
                PlayerActions.openDoor()
                break
            case ActionListManager.GET_CARD_FROM_STACK:
                PlayerActions.getCardFromStack()
                break
            case ActionListManager.SELECT_CLASS:
                PlayerActions.selectClass()
                break
            case ActionListManager.SELECT_RACE:
                PlayerActions.selectRace()
                break
            case ActionListManager.GET_DOOR:
                PlayerActions.getCardInclosed()
            case ActionListManager.NEXT:
                NPCProcessor.WAIT = false
                break
            case ActionListManager.NEXT_TURN:
                GameProcessor.instance.nextTurn()
                break
            case ActionListManager.USE_ITEM:
                PlayerActions.useItem()
                break
            case ActionListManager.CAST_SPELL:
                PlayerActions.castSpell()
                break
            case ActionListManager.TRY_RUN:
                break
            case ActionListManager.NEXT_BATTLE_ROUND:
                Fight.nextBattleRound()
                break
            case ActionListManager.GET_FAIL:
                GameProcessor.instance.fighting.getObscenity()
                break
            case ActionListManager.GET_GOLD:
                GameProcessor.instance.fighting.getSuccess()
                break
            case ActionListManager.FIGHT_WITH_HAND_MON:
                PlayerActions.fightWithMonsterFromHand()
                break
            case ActionListManager.EQUIP_ITEM:
                PlayerActions.equipItem()
                break
        }
    }
}
