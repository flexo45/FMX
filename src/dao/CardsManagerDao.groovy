package dao

import cards.Curse
import cards.Monster
import cards.Profession
import cards.Race
import cards.Item
import effect.Action
import effect.Effect
import effect.Property
import effect.Target
import equipment.EquipmentItem
import itemmanager.ItemSize
import itemmanager.ItemType
import log.Log

class CardsManagerDao {

    private static Node cards_set
    private static List<Map> mapItemTypes = new ArrayList<>()
    private static List<Map> mapItemSize = new ArrayList<>()
    private static List<Map> mapEffect = new ArrayList<>()

    public static List<Map<String, EquipmentItem>> getEquipmentMap(){

        readCardsSet()

        List<Map> list = new ArrayList<>()

        ((Node)cards_set.find{ it.name().equals('equip_map')}).each {
            String place = it.attribute('place')
            ItemType type = getItemTypeById(Integer.parseInt(it.attribute('type').toString()))
            int cell = Integer.parseInt(it.attribute('cell').toString())

            EquipmentItem equipmentItem = new EquipmentItem(type, cell)

            list.add([name: place, item: equipmentItem])
        }

        return list
    }

    public static readCardsSet() throws Exception{

        GameManagerDao.readConfig()

        String card_set_path = GameManagerDao.getCardSetPath()

        FileReader fileReader

        try{
            fileReader = new FileReader(card_set_path)
        }
        catch (Exception e){
            throw new Exception("OPEN_CARDSET_ERROR: ${e.toString()}")
        }

        try{
            BufferedReader bufferedReader = new BufferedReader(fileReader)

            String c_text = ""
            String line = ""

            while ((line = bufferedReader.readLine()) != null){
                c_text += line
            }

            bufferedReader.close()

            XmlParser parser = new XmlParser()
            cards_set = parser.parseText(c_text)
        }
        catch (Exception e){
            throw new Exception("READ_CARDSET_ERROR: ${e.toString()}")
        }

        Log.print(this, "read card set success")
    }

    public static List<Map> getGoldsSet(){

        readCardsSet()

        readItemTypes()

        readItemSizes()

        readEffects()

        List<Map> list = []

        Node golds = (Node)cards_set.find{ it.name().equals('golds') }

        ((Node)golds.find{ it.name().equals('items') }).children().each {
            Node i ->

                list.add([id: Integer.parseInt(i.attribute('id').toString()),
                          card: new Item(name: i.attribute('name')
                                  , type: getItemTypeById(Integer.parseInt(i.attribute('type').toString()))
                                  , size: getItemSizeById(Integer.parseInt(i.attribute('size').toString()))
                                  , effect: getEffectById(Integer.parseInt(i.attribute('effect').toString()))
                                  , power: Integer.parseInt(i.attribute('power').toString()))

                          , count: i.attribute('count')?:0])
        }

        Log.print(this, "get gold set success")

        return list
    }

    private static readEffects(){
        Node effects = (Node)cards_set.find{ it.name().equals('effects') }

        effects.each {
            Node i ->
                mapEffect.add([id:Integer.parseInt(i.attribute('id').toString())
                                 , effect: new Effect(target: Target.parseString(i.attribute('target').toString())
                        , property: Property.parseString(i.attribute('property').toString())
                        , action: Action.parseString(i.attribute('action').toString()))])
        }

        Log.print(this, "effect readed success: ${mapEffect}")
    }

    private static readItemSizes(){
        Node item_size = (Node)cards_set.find{ it.name().equals('item_size') }

        item_size.each {
            Node i ->
                mapItemSize.add([id:Integer.parseInt(i.attribute('id').toString())
                                  , size: new ItemSize(name: i.attribute('name'))])
        }

        Log.print(this, "item size readed success: ${mapItemSize}")
    }

    private static readItemTypes(){
        Node item_types = (Node)cards_set.find{ it.name().equals('item_types') }

        item_types.each {
            Node i ->
                mapItemTypes.add([id:Integer.parseInt(i.attribute('id').toString())
                                     , type: new ItemType(name: i.attribute('name'))])
        }

        Log.print(this, "item types readed success: ${mapItemTypes}")
    }

    public static List<Map> getDoorsSet(){
        readCardsSet()

        List<Map> list = []

        Node doors = (Node)cards_set.find{ it.name().equals('doors') }

        ((Node)doors.find{ it.name().equals('race') }).children().each {
            Node c ->
                list.add([id: Integer.parseInt(c.attribute('id').toString()),
                card: new Race(name: c.attribute('name')), count: c.attribute('count')?:0])
        }

        ((Node)doors.find{ it.name().equals('professon') }).children().each {
            Node c ->
                list.add([id: Integer.parseInt(c.attribute('id').toString()),
                          card: new Profession(name: c.attribute('name')), count: c.attribute('count')?:0])
        }

        ((Node)doors.find{ it.name().equals('monster') }).children().each {
            Node c ->
                list.add([id: Integer.parseInt(c.attribute('id').toString()),
                          card: new Monster(name: c.attribute('name'), level: Integer.parseInt((String)c.attribute('level')))
                          , count: c.attribute('count')?:0])
        }

        ((Node)doors.find{ it.name().equals('curse') }).children().each {
            Node c ->
                list.add([id: Integer.parseInt(c.attribute('id').toString()),
                          card: new Curse(name: c.attribute('name')), count: c.attribute('count')?:0])
        }

        Log.print(this, "door set readed success")

        return list
    }

    private static ItemSize getItemSizeById(Integer id){
        ItemSize itemSize = null
        mapItemSize.each {
            if(it.get('id') == id){
                itemSize = (ItemSize)it.get('size')
            }
        }

        Log.print(this, "ItemSize: found $itemSize by id $id")

        return itemSize
    }

    private static ItemType getItemTypeById(Integer id){
        ItemType itemType = null
        mapItemTypes.each {
            if(it.get('id') == id){
                itemType = (ItemType)it.get('type')
            }
        }

        Log.print(this, "ItemType: found $itemType by id $id")

        return itemType
    }

    private static Effect getEffectById(Integer id){
        Effect effect = null
        mapEffect.each {
            if(it.get('id') == id){
                effect = (Effect)it.get('effect')
            }
        }

        Log.print(this, "Effect: found $effect by id $id")

        return effect
    }
}
