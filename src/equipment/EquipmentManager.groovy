package equipment

import cards.Item
import gamedata.CardDataManager
import itemmanager.ItemType

class EquipmentManager {

    private static List<EquipmentItem> equipmentItemList = CardDataManager.instance.getEquipmentMap()

    public static EquipmentItem getEquipmentItem(ItemType itemType){
        EquipmentItem item = null
        equipmentItemList.each {
            if(it.itemType.name.equals(itemType.name)){
                item = it
            }
        }
        return item
    }

    public static String getPlace(ItemType itemType){
        String place = ""
        equipmentItemList.each {
            if(it.itemType.name.equals(itemType.name)){
                place = it.place
            }
        }
        return place
    }

    public static boolean isEquipment(Item item){
        boolean result = false
        equipmentItemList.each {
            if(it.itemType.name.equals(item.type.name)){
                result = true
            }
        }
        return result
    }

}
