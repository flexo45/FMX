package equipment

import cards.Item
import dao.CardsManagerDao
import itemmanager.ItemType

class EquipmentManager {

    private static List<Map> equipmentItemList = CardsManagerDao.getEquipmentMap()

    public static List<Map> getEquipmentMap(){return equipmentItemList}

    public static List<String> getEquipmentPlaces(){
        List<String> list = new ArrayList<>()

        equipmentItemList.each {
            list.add(it.get('name').toString())
        }

        return list
    }

    public static EquipmentItem getEquipmentItem(ItemType itemType){
        EquipmentItem item = null
        equipmentItemList.each {
            if(((EquipmentItem)it.get('item')).itemType.equals(itemType)){
                item = (EquipmentItem)it.get('item')
            }
        }
        return item
    }

    public static EquipmentItem getEquipmentItem(String place){
        EquipmentItem item = null
        equipmentItemList.each {
            if(it.get('name').equals(place)){
                item = (EquipmentItem)it.get('item')
            }
        }
        return item
    }

    public static String getPlace(ItemType itemType){
        String place = ""
        equipmentItemList.each {
            if(((EquipmentItem)it.get('item')).itemType.equals(itemType)){
                place = it.get('name')
            }
        }
        return place
    }



    public static boolean isEquipment(Item item){
        boolean result = false
        equipmentItemList.each {
            if(((EquipmentItem)it.get('item')).itemType.equals(item.type)){
                result = true
            }
        }
        return result
    }

}
