package effect

import cards.Monster
import effect.handler.ChangeHandler
import effect.handler.LoseHandler
import effect.handler.VaryHandler
import fightmanager.Fight
import gamemanager.GameProcessor
import gamemanager.Player

class EffectManager {

    public static void processEffect(Object target, Effect effect, int power) throws Exception{
        if(target.getClass().equals(Player.class)){
            if(effect.target.equals(Target.PLAYER)){
                if(effect.action.equals(Action.CHANGE)){
                    ChangeHandler.processPlayerTarget(target as Player, effect.property, power)
                }
                else if(effect.action.equals(Action.VARY)){
                    VaryHandler.processPlayerTarget(target as Player, effect.property, power)
                }
                else if(effect.action.equals(Action.LOSE)){
                    LoseHandler.processPlayerTarget(target as Player, effect.property, power
                            , effect.itemSize, effect.itemType)
                }
            }
            else {
                throw new Exception("invalid target: $effect.target type")
            }
        }
        else if(target.getClass().equals(Monster.class)){
            if(effect.target.equals(Target.MONSTER)){
                if(effect.action.equals(Action.VARY)){
                    VaryHandler.processMonsterTarget(target as Monster, effect.property, power)
                }
            }
            else {
                throw new Exception("invalid target: $effect.target type")
            }
        }
    }
}
