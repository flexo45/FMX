package gamedata.dao

import effect.Action
import effect.Property
import effect.Target
import gamedata.CardDataManager
import gamedata.dataset.EffectDataSet

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
                            , itemType: Long.valueOf((effect.attribute('type')?:"0").toString())
                            , itemSize: Long.valueOf((effect.attribute('size')?:"0").toString())
                            , action: Action.parseString(effect.attribute('action').toString()))
                }
        }

        return effectDataSet
    }
}
