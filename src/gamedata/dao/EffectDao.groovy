package gamedata.dao

import effect.Action
import effect.Property
import effect.Target
import gamedata.CardDataManager
import gamedata.dataset.EffectDataSet

/*<effects>
		<effect id="1001" target="player" property="level" action="increase" />
		<effect id="1002" target="player" property="level" action="decrease" />
		<effect id="2001" target="player" property="profession" action="change"/>
		<effect id="4001" target="player" property="rating" action="increase" />
		<effect id="4002" target="player" property="rating" action="decrease" />
	</effects>*/

class EffectDao {
    public EffectDataSet get(Long id) {

        EffectDataSet effectDataSet = null

        (CardDataManager.instance.getCardSet().children().find{ Node n -> n.name().equals('effects')} as Node)
                .children()
                .each {

            Node effect ->
                if(Long.valueOf(effect.attribute('id').toString()) == id){
                    effectDataSet = new EffectDataSet(id: Long.valueOf(effect.attribute('id').toString())
                            , target: Target.parseString(effect.attribute('target').toString())
                            , property: Property.parseString(effect.attribute('property').toString())
                            , action: Action.parseString(effect.attribute('action').toString()))
                }
        }

        return effectDataSet
    }
}
