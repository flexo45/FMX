package ai

import cards.Item
import cards.Profession
import cards.Race
import cards.Spell
import effect.Target
import gamemanager.GameProcessor
import gamemanager.Player
import interfaces.IClass
import interfaces.IRace
import log.Log

class MainLogic {

    public MainLogic(Player npc){
        this.npc = npc
    }

    private Player npc

    public Player getNpc() {return npc}

    private static Log logger = new Log(MainLogic.class.name)

    boolean changeRace(){
        def result = false
        IRace race = (IRace)npc.hand.find{ it.class.equals(Race.class)}
        if(race != null){
            if(npc.race == GameProcessor.instance.game.defaultRace) result = true
            return result
        }
        return result
    }

    boolean changeProfession(){
        def result = false
        IClass profession = (IClass)npc.hand.find{ it.class.equals(Profession.class)}
        if(profession != null){
            if(npc.c1ass == GameProcessor.instance.game.defaultClass) result = true
            return result
        }
        return result
    }

    IRace selectRace(){
        IRace race = (IRace)npc.hand.find{ it.class.equals(Race.class)}
        return race
    }

    IClass selectClass(){
        IClass c1ass = (IClass)npc.hand.find{ it.class.equals(Profession.class)}
        return c1ass
    }

    boolean getFirstDoorCard(){
        return true
    }

    void tryIncreaseRatingInBattle(){

        npc.hand.each {
            if(it.class.equals(Spell.class)){
                Spell spell = (it as Spell)
                if(spell.effect.target == Target.MONSTER && spell.power < 0){
                    spell.cast(GameProcessor.instance.fighting.monster)
                    logger.info("${npc.name} case spell ${spell.name} on monster")
                }
            }
            else if(it.class.equals(Item.class)){
                Item item = (it as Item)
                if(item.effect.target == Target.MONSTER && item.power < 0){
                    item.useIt(GameProcessor.instance.fighting.monster)
                    logger.info("${npc.name} use item ${item.name} on monster")
                }
                else if(item.effect.target == Target.PLAYER && item.power > 0){
                    item.useIt(npc)
                    logger.info("${npc.name} use item ${item.name} on self")
                }
            }
        }
    }
}
