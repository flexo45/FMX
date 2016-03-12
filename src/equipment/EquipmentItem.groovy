package equipment

import itemmanager.ItemType

class EquipmentItem {
    public EquipmentItem(ItemType itemType, int max_cell, String place){
        this.itemType = itemType
        max_cells = max_cell
        this.place = place
    }

    private String place
    private int max_cells
    private ItemType itemType

    public int getMaxCells() {return max_cells}
    public ItemType getItemType() {return itemType}
    public String getPlace() {return place}

    @Override
    public String toString(){
        return "equipmentItem[place:$place, itemType:$itemType, max_cell:$max_cells]"
    }
}
