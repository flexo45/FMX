package equipment

import cards.Item

class Equipment {

    List<Map> equipment = []

    public void addItem(Item item){
        if(EquipmentManager.isEquipment(item)){
            EquipmentItem equipmentItem = EquipmentManager.getEquipmentItem(item.type)
            if(getEitherItems(equipmentItem).count(Map) < equipmentItem.maxCells){
                equipment.add([eq_item: equipmentItem, item: item])
            }
            else {
                throw new Exception("NO_FREE_CELL_EXCEPTION")
            }
        }
        else {
            throw new Exception("NOT_EQUIPMENT_EXCEPTION")
        }
    }

    private List<Map> getEitherItems(EquipmentItem equipmentItem){
        List<Map> list = []
        equipment.each {
            if(it.get('eq_item').equals(equipmentItem)){
                list.add(it)
            }
        }
        return list
    }
}
