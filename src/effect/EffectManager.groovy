package effect

import cards.Monster
import fightmanager.Fight
import gamemanager.GameProcessor
import gamemanager.Player

class EffectManager {

    public static void processEffect(Object target, Effect effect, int power) throws Exception{

        if(target.getClass().equals(Player.class)){
            if(effect.target.equals(Target.PLAYER)){
                if(effect.property.equals(Property.RATING)){
                    def rating = (target as Player).rating
                    if(effect.action.equals(Action.VARY)){
                        (target as Player).rating = rating + power
                    }
                    else if(effect.action.equals(Action.CHANGE)){
                        (target as Player).rating = power
                    }
                    else {
                        throw new Exception("invalid acion: $effect.action type")
                    }
                }
                else if(effect.property.equals(Property.LEVEL)){
                    def lvl = (target as Player).level
                    if(effect.action.equals(Action.CHANGE)){
                        (target as Player).level = power
                    }
                    else if(effect.action.equals(Action.VARY)){
                        (target as Player).level = (lvl + power) < 1 ? 1 : ((lvl + power) > 10 ? 10 : (lvl + power))
                    }
                    else {
                        throw new Exception("invalid acion: $effect.action type")
                    }
                }
                else if(effect.property.equals(Property.PROFESSION)){
                    if(effect.action.equals(Action.CHANGE)){
                        (target as Player).c1ass //new Profession
                    }
                    else if(effect.action.equals(Action.LOSE)){
                        (target as Player).c1ass = GameProcessor.instance.game.defaultClass
                    }
                    else {
                        throw new Exception("invalid acion: $effect.action type")
                    }
                }
                else if(effect.property.equals(Property.RACE)){
                    if(effect.action.equals(Action.CHANGE)){
                        (target as Player).c1ass //new Race
                    }
                    else if(effect.action.equals(Action.LOSE)){
                        (target as Player).c1ass = GameProcessor.instance.game.defaultClass
                    }
                    else {
                        throw new Exception("invalid acion: $effect.action type")
                    }
                }
                else if(effect.property.equals(Property.ITEM)){
                    if(effect.action.equals(Action.LOSE)){
                        (target as Player).equipment //remove item of effect.itemSize or effect.itemType
                    }
                    else {
                        throw new Exception("invalid acion: $effect.action type")
                    }
                }
                else {
                    throw new Exception("invalid property type")
                }
            }
            else {
                throw new Exception("invalid target: $effect.target type")
            }
        }
        else if(target.getClass().equals(Monster.class)){
            if(effect.target.equals(Target.MONSTER)){
                Fight fight = GameProcessor.instance.fighting
                if(fight != null){
                    if(effect.property.equals(Property.LEVEL)){
                        if(effect.action.equals(Action.VARY)){
                            fight.changeMonsterBuff(power)
                        }
                        else {
                            throw new Exception("invalid acion: $effect.action type")
                        }
                    }
                }
                else {
                    throw new Exception("no active fight")
                }
            }
            else {
                throw new Exception("invalid target: $effect.target type")
            }
        }
    }

}
