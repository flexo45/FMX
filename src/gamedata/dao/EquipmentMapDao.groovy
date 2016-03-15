package gamedata.dao

import gamedata.CardDataManager
import gamedata.dataset.EquipMapDataSet

class EquipmentMapDao {

    public List<EquipMapDataSet> getMap(){

        List<EquipMapDataSet> equipMapDataSetMap = []

        (CardDataManager.instance.getCardSet().children().find{ Node n -> n.name().equals('equip_map')} as Node)
                .children()
                .each {

            Node n ->

                EquipMapDataSet equipMapDataSet = new EquipMapDataSet(place: n.attribute('place')
                        , type: Long.valueOf(n.attribute('type').toString())
                        , cell: Integer.valueOf(n.attribute('cell').toString()))

                equipMapDataSetMap.add(equipMapDataSet)

        }

        return equipMapDataSetMap
    }

}
