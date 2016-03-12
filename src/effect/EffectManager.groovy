package effect

import gamemanager.Player

class EffectManager {

    public static void processEffect(Object target, Effect effect, int power){

        if(target.getClass().equals(Player.class)){
            if(effect.target.equals(Target.PLAYER)){
                if(effect.property.equals(Property.RATING)){
                    def rating = (target as Player).rating
                    if(effect.action.equals(Action.DECREASE)){
                        (target as Player).rating = rating - power
                    }
                    else if(effect.action.equals(Action.INCREASE)){
                        (target as Player).rating = rating + power
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
                    else if(effect.action.equals(Action.DECREASE)){
                        (target as Player).level = (lvl - power) < 1 ? 1 : (lvl - power)
                    }
                    else if(effect.action.equals(Action.INCREASE)){
                        (target as Player).level = (lvl + power) > 10 ? 10 : (lvl + power)
                    }
                    else {
                        throw new Exception("invalid acion: $effect.action type")
                    }
                }
                else if(effect.property.equals(Property.PROFESSION)){
                    if(effect.action.equals(Action.CHANGE)){
                        (target as Player).level = power
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
    }

}
