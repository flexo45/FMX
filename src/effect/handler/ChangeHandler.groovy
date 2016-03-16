package effect.handler

import cards.Monster
import cards.Profession
import cards.Race
import effect.Property
import effect.Target
import gamemanager.GameProcessor
import gamemanager.Player
import interfaces.ICard
import interfaces.IClass
import interfaces.IRace

class ChangeHandler {

    public static void processPlayerTarget(Player target, Property property, int power){
        switch (property){
            case Property.LEVEL:
                target.level = power
                break
            case Property.PROFESSION:
                target.c1ass = getLastClassInDrop()
                break
            case Property.RACE:
                target.race = getLastRaceInDrop()
                break
        }
    }

    private static IRace getLastRaceInDrop(){
        List<ICard> reversedDrop = GameProcessor.instance.game.doors.drop.reverse()
        IRace race = null
        reversedDrop.each {
            if(it.class.name.equals(Race.class.name)){
                race = it as IRace
            }
        }

        if(race == null){
            race = GameProcessor.instance.game.defaultRace
        }

        return race
    }

    private static IClass getLastClassInDrop(){
        List<ICard> reversedDrop = GameProcessor.instance.game.doors.drop.reverse()
        IClass c1ass = null
        reversedDrop.each {
            if(it.class.name.equals(Profession.class.name)){
                c1ass = it as Profession
            }
        }

        if(c1ass == null){
            c1ass = GameProcessor.instance.game.defaultClass
        }

        return c1ass
    }
}
