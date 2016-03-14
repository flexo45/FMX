package gamedata.dao

import gamedata.CardDataManager
import gamedata.dataset.ItemSizeDataSet
import gamedata.dataset.ItemTypeDataSet

class ItemPropertiesDao {

    public ItemTypeDataSet getItemType(Long id){

        ItemTypeDataSet itemTypeDataSet = null

        (CardDataManager.instance.getCardSet().children().find{ Node n -> n.name().equals('item_types')} as Node)
                .children()
                .each {

            Node node ->
                if(Long.valueOf(node.attribute('id').toString()) == id){
                    itemTypeDataSet = new ItemTypeDataSet(id: Long.valueOf(node.attribute('id').toString())
                            , type: node.attribute('type'))
                }
        }

        return itemTypeDataSet
    }

    public ItemSizeDataSet getItemSize(Long id){

        ItemSizeDataSet itemSizeDataSet = null

        (CardDataManager.instance.getCardSet().children().find{ Node n -> n.name().equals('item_size')} as Node)
                .children()
                .each {

            Node node ->
                if(Long.valueOf(node.attribute('id').toString()) == id){
                    itemSizeDataSet = new ItemSizeDataSet(id: Long.valueOf(node.attribute('id').toString())
                            , type: node.attribute('type'))
                }
        }

        return itemSizeDataSet

    }
}
