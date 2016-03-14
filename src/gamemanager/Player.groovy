package gamemanager

import cards.Item
import effect.Effect
import effect.EffectManager
import equipment.Equipment
import interfaces.ICard
import interfaces.IClass
import interfaces.IRace

class Player {
    String name
    Boolean sex
    IRace race = null
    IClass c1ass = null
    List<ICard> hand = []
    Boolean npc = false
    Integer level = 1
    Integer rating = 0
    Equipment equipment = new Equipment();
    List<Effect> effects = new ArrayList<>();

    public int getRating(){return rating}
    public void setRating(int rating){this.rating = rating}

    public int getBaseRating(){
        rating = 0
        equipment.getAllItems().each {
            Item item ->
                EffectManager.processEffect(this, item.effect, item.power)
        }
        return (level + rating) < 1 ? 1 : (level + rating)
    }

    @Override
    public String toString(){
        return "player[name:$name, sex:$sex, race:$race, class:$c1ass, hand:$hand, npc:$npc, level:$level]"
    }
}
