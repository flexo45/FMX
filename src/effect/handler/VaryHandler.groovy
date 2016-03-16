package effect.handler

import cards.Monster
import effect.Action
import effect.Property
import fightmanager.Fight
import gamemanager.GameProcessor
import gamemanager.Player

class VaryHandler {

    public static void processPlayerTarget(Player target, Property property, int power){
        switch (property){
            case Property.LEVEL:
                def lvl = target.level
                target.level = (lvl + power) < 1 ? 1 : ((lvl + power) > 10 ? 10 : (lvl + power))
                break
            case Property.RATING:
                def rating = target.rating
                target.rating = rating + power
                break
        }
    }

    public static void processMonsterTarget(Monster target, Property property, int power){
        Fight fight = GameProcessor.instance.fighting
        if(fight == null){
            throw new Exception("Fighting not found")
        }
        switch (property){
            case Property.LEVEL:
                def lvl = fight.monster.level
                fight.monster.level = lvl + power
                break
            case Property.RATING:
                fight.changeMonsterBuff(power)
                break
        }
    }
}
