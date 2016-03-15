package gamedata.dao

import cards.Item
import gamedata.CardDataManager
import gamedata.dataset.GoldDataSet
import log.Log

class GoldDao {

    private static Log logger = new Log(GoldDao.class.name)

    public GoldDataSet getGold(Long id){

        GoldDataSet goldDataSet = null

        (CardDataManager.instance.getCardSet().children().find{ Node n -> n.name().equals('golds')} as Node)
                .children()
                .each {
            Node group ->
                group.children().each {
                    Node card ->
                        if(Long.valueOf(card.attribute('id').toString()) == id){
                            goldDataSet = new GoldDataSet(id: Long.valueOf(card.attribute('id').toString())
                                    , name: card.attribute('name')
                                    , cardType: getClassOfType(group.name().toString())
                                    , type: Long.valueOf(card.attribute('type').toString())
                                    , size: Long.valueOf(card.attribute('size').toString())
                                    , effect: Long.valueOf((card.attribute('effect')?:"0").toString())
                                    , power: Integer.valueOf((card.attribute('power')?:"0").toString())
                                    , count: Integer.valueOf((card.attribute('count')?:"0").toString())
                                    , cell: Integer.valueOf((card.attribute('cell')?:"1").toString())
                                    )
                        }
                }
        }

        return goldDataSet
    }

    public List<Long> getIdGolds(){
        List<Long> list = []

        (CardDataManager.instance.getCardSet().children().find{Node n -> n.name().equals('golds')} as Node)
                .children()
                .each {
            Node group ->
                group.children().each {
                    Node card ->
                        list.add(Long.valueOf(card.attribute('id').toString()))
                }
        }

        return list
    }

    private Class getClassOfType(String type){

        Class classOf = null

        switch (type){
            case 'items':
                classOf = Item.class
                break
            default:
                logger.error("Invalid gold type: $type", new Exception("Unexpected gold type"))
        }

        return classOf
    }

}
