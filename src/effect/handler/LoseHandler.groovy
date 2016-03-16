package effect.handler

import cards.Item
import effect.Property
import gamemanager.GameProcessor
import gamemanager.Player
import itemmanager.ItemSize
import itemmanager.ItemType
import log.Log
import ui.gametable.SelectCardPopup
import ui.gametable.SelectItemPopup

class LoseHandler {

    private static Log logger = new Log(LoseHandler.class.name)

    public static void processPlayerTarget(Player target, Property property, int power, ItemSize size, ItemType type){
        switch (property){
            case Property.LEVEL:
                def lvl = target.level
                target.level = lvl - power < 1 ? 1 : lvl - power
                break
            case Property.PROFESSION:
                target.c1ass = GameProcessor.instance.game.defaultClass
                break
            case Property.RACE:
                target.race = GameProcessor.instance.game.defaultRace
                break
            case Property.ITEM:
                List<Item> items = target.equipment.getAllItems()
                List<Item> items_for_lose = []
                if(size != null){
                    items.each {
                        if(it.size.name.equals(size.name)){
                            items_for_lose.add(it)
                        }
                    }
                }
                else if(type != null){
                    items.each {
                        if(it.type.name.equals(type.name)){
                            items_for_lose.add(it)
                        }
                    }
                }
                else {
                    throw new Exception("Invalid criteria for lose item")
                }

                if(items_for_lose.size() == 0){
                    logger.info("player ${target.name} have not ${type.name}${size.name} item for lose")
                }
                else if(items_for_lose.size() == 1){
                    Item item = items_for_lose.get(0)
                    target.equipment.equipment.remove(item)
                    logger.info("player ${target.name} lose ${item}")
                }
                else {
                    SelectItemPopup.initialize(items_for_lose)
                    Item item = GameProcessor.instance.selectedItem
                    if(item != null){
                        target.equipment.equipment.remove(item)
                        logger.info("player ${target.name} lose ${item}")
                    }
                    else {
                        throw new Exception("Invalid item for lose")
                    }
                }
                break
        }
    }
}
