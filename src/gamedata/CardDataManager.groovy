package gamedata

import cards.Curse
import cards.Monster
import cards.Profession
import cards.Race
import cards.Item
import cards.Spell
import effect.Action
import effect.Effect
import effect.Property
import effect.Target
import equipment.EquipmentItem
import gamedata.GameDataManager
import gamedata.dao.CardDataDao
import gamedata.dao.DoorDao
import gamedata.dao.EffectDao
import gamedata.dao.EquipmentMapDao
import gamedata.dao.GoldDao
import gamedata.dao.ItemPropertiesDao
import gamedata.dataset.DoorDataSet
import gamedata.dataset.EffectDataSet
import gamedata.dataset.GoldDataSet
import gamedata.dataset.ItemSizeDataSet
import gamedata.dataset.ItemTypeDataSet
import interfaces.ICard
import itemmanager.ItemSize
import itemmanager.ItemType
import log.Log

class CardDataManager {

    private static Log logger = new Log(CardDataManager.class.name)

    private Node cards_set
    private String CARD_SET_PATH
    private CardDataDao cardDataDao
    private GoldDao goldDao
    private DoorDao doorDao
    private EffectDao effectDao
    private ItemPropertiesDao itemPropertiesDao
    private EquipmentMapDao equipmentMapDao

    private static CardDataManager instance

    public static CardDataManager getInstance(){
        if(instance == null) instance = new CardDataManager()
        return instance
    }

    private CardDataManager(){
        CARD_SET_PATH = GameDataManager.instance.getCardSetPath()
        cardDataDao = new CardDataDao()
        doorDao = new DoorDao()
        goldDao = new GoldDao()
        effectDao = new EffectDao()
        itemPropertiesDao = new ItemPropertiesDao()
        equipmentMapDao = new EquipmentMapDao()
        cards_set = cardDataDao.getCardSet(CARD_SET_PATH)

        logger.debug("Card set readed from file: $CARD_SET_PATH")
    }

    public Node getCardSet(){
        checkCardSet()
        return cards_set
    }

    public ICard getCard(Long id){
        ICard card = null

        DoorDataSet doorDataSet = doorDao.getDoor(id)

        if(doorDataSet != null){
            switch (doorDataSet.type){
                case Race.class:
                    card = new Race()
                    break
                case Profession.class:
                    card = new Profession()
                    break
                case Monster.class:
                    card = new Monster()
                    (card as Monster).level = doorDataSet.level

                    EffectDataSet effectDataSet = effectDao.get(doorDataSet.obscenity)
                    (card as Monster).obscenity = getEffect(effectDataSet)

                    (card as Monster).obscenity_value = doorDataSet.obscenity_value
                    (card as Monster).gold = doorDataSet.gold
                    (card as Monster).add_level = doorDataSet.add_level
                    break
                case Curse.class:
                    card = new Curse()
                    break
                case Spell.class:
                    card = new Spell()
                    (card as Spell).power = doorDataSet.power
                    break
                default:
                    logger.error("Invalid DoorDataSet type ${doorDataSet.type}"
                            , new Exception("Unexpected door type"))
            }
            card.id = doorDataSet.id
            card.name = doorDataSet.name
            card.info = doorDataSet.info
            EffectDataSet effectDataSet = effectDao.get(doorDataSet.effect)
            if(effectDataSet != null){
                card.effect = getEffect(effectDataSet)
            }
        }
        else {
            GoldDataSet goldDataSet = goldDao.getGold(id)

            if(goldDataSet != null){
                switch (goldDataSet.cardType){
                    case Item.class:
                        card = new Item()
                        (card as Item).power = goldDataSet.power
                        (card as Item).size = getItemSize(itemPropertiesDao.getItemSize(goldDataSet.size))
                        (card as Item).type = getItemType(itemPropertiesDao.getItemType(goldDataSet.type))
                        (card as Item).cell = goldDataSet.cell
                        break
                    default:
                        logger.error("Invalid GoldDataSet type ${goldDataSet.type}"
                                , new Exception("Unexpected gold type"))
                }
                card.id = goldDataSet.id
                card.name = goldDataSet.name
                EffectDataSet effectDataSet = effectDao.get(goldDataSet.effect)
                if(effectDataSet != null){
                    card.effect = getEffect(effectDataSet)
                }
            }
            else {
                logger.error("Card not found with id=$id", new Exception("Card not found"))
            }
        }

        logger.debug("$card readed")

        return card
    }

    public List<Long> getDoorsListId(){
        return doorDao.getIdDoors()
    }

    public List<Long> getGoldsListId(){
        return goldDao.getIdGolds()
    }

    public int getCardCountInSet(Long id){
        DoorDataSet doorDataSet = doorDao.getDoor(id)
        if(doorDataSet == null){
            GoldDataSet goldDataSet = goldDao.getGold(id)
            if(goldDataSet == null){
                return -1
            }
            else {
                return goldDataSet.count
            }
        }
        else {
            return doorDataSet.count
        }
    }

    public List<EquipmentItem> getEquipmentMap(){

        List<EquipmentItem> list = new ArrayList<>()

        equipmentMapDao.getMap().each {
            EquipmentItem equipmentItem = new EquipmentItem(
                    getItemType(itemPropertiesDao.getItemType(it.type)), it.cell, it.place)

            list.add(equipmentItem)
        }

        logger.debug("equipment map readed: $list")

        return list
    }

    private void checkCardSet(){
        if(cardDataDao.isCasdSetOutdate()){
            cards_set = cardDataDao.getCardSet(CARD_SET_PATH)
            logger.debug("Card set updated")
        }
    }

    private Effect getEffect(EffectDataSet effectDataSet){
        return new Effect(id: effectDataSet.id
                , target: effectDataSet.target
                , property: effectDataSet.property
                , action: effectDataSet.action
                , itemSize: getItemSize(itemPropertiesDao.getItemSize(effectDataSet.itemSize))
                , itemType: getItemType(itemPropertiesDao.getItemType(effectDataSet.itemType)))
    }

    private ItemSize getItemSize(ItemSizeDataSet itemSizeDataSet){
        if(itemSizeDataSet == null){ return null }
        return new ItemSize(name: itemSizeDataSet.type)
    }

    private ItemType getItemType(ItemTypeDataSet itemTypeDataSet){
        if(itemTypeDataSet == null) {return null}
        return new ItemType(name: itemTypeDataSet.type)
    }
}
