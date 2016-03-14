package equipment

import cards.Item

class Equipment {

    List<Map> equipment = []

    public List<Item> getAllItems(){
        ArrayList<Item> items = []
        equipment.each {
            items.add((Item)it.get('item'))
        }
        return items
    }

    public void addItem(Item item) throws NoFreeCellException, NotEquipmenItemException{
        if(EquipmentManager.isEquipment(item)){
            EquipmentItem equipmentItem = EquipmentManager.getEquipmentItem(item.type)
            if(getEitherItems(equipmentItem).count(Map) < equipmentItem.maxCells){
                equipment.add([eq_item: equipmentItem, item: item])
            }
            else {
                throw new NoFreeCellException("NO_FREE_CELL_EXCEPTION")
            }
        }
        else {
            throw new NotEquipmenItemException("NOT_EQUIPMENT_EXCEPTION")
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
