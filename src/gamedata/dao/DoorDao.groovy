package gamedata.dao

import cards.Curse
import cards.Event
import cards.Monster
import cards.Profession
import cards.Race
import cards.Spell
import gamedata.CardDataManager
import gamedata.dataset.DoorDataSet
import log.Log

class DoorDao {

    private static Log logger = new Log(DoorDao.class.name)

    public DoorDataSet getDoor(Long id){

        DoorDataSet doorDataSet = null

        (CardDataManager.instance.getCardSet().children().find{Node n -> n.name().equals('doors')} as Node)
                .children()
                .each {
            Node group ->
                group.children().each {
                    Node card ->
                        if(Long.valueOf(card.attribute('id').toString()) == id){
                            doorDataSet = new DoorDataSet(id: Long.valueOf(card.attribute('id').toString())
                                    , name: card.attribute('name')
                                    , type: getClassOfType(group.name().toString())
                                    , info: card.attribute('info')
                                    , effect: Long.valueOf((card.attribute('effect')?:"0").toString())
                                    , count: Integer.valueOf((card.attribute('count')?:"0").toString())
                                    , obscenity: Long.valueOf((card.attribute('obscenity')?:"0").toString())
                                    , obscenity_value: Integer.valueOf((card.attribute('obscenity_value')?:"0").toString())
                                    , gold: Integer.valueOf((card.attribute('gold')?:"0").toString())
                                    , add_level: Integer.valueOf((card.attribute('add_level')?:"1").toString())
                                    , power: Integer.valueOf((card.attribute('power')?:"0").toString())
                                    , level: Integer.valueOf((card.attribute('level')?:"0").toString()))
                        }
                }
        }

        return doorDataSet
    }

    public List<Long> getIdDoors(){
        List<Long> list = []

        (CardDataManager.instance.getCardSet().children().find{Node n -> n.name().equals('doors')} as Node)
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
            case 'race':
                classOf = Race.class
                break
            case 'professon':
                classOf = Profession.class
                break
            case 'monster':
                classOf = Monster.class
                break
            case 'curse':
                classOf = Curse.class
                break
            case 'spell':
                classOf = Spell.class
                break
            case 'event':
                classOf = Event.class
                break
            default:
                logger.error("invalid door type", new Exception("Unexpected door type"))
                Log.print(this, "ERROR: invalid type: $type")
        }

        return classOf
    }
}
