package gamedata.dataset

import effect.Action
import effect.Property
import effect.Target

/**
 * <effects>
 <effect id="1001" target="player" property="level" action="increase" />
 <effect id="1002" target="player" property="level" action="decrease" />
 <effect id="2001" target="player" property="profession" action="change"/>
 <effect id="4001" target="player" property="rating" action="increase" />
 <effect id="4002" target="player" property="rating" action="decrease" />
 </effects>
 */
class EffectDataSet {
    Long id
    Target target
    Property property
    Action action

    @Override
    public String toString(){
        return "effectDataSet[id:$id, target:$target, property:$property, action:$action]"
    }
}
