package equipment

import itemmanager.ItemType

class EquipmentItem {
    public EquipmentItem(ItemType itemType, int max_cell){
        this.itemType = itemType
        max_cells = max_cell
    }

    private int max_cells
    private ItemType itemType

    public int getMaxCells() {return max_cells}
    public ItemType getItemType() {return itemType}
}
