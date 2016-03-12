package gamedata.dataset

/**
 * <item_types>
 <item id="101" type="weapon" />
 <item id="102" type="armor" />
 <item id="103" type="boots" />
 <item id="104" type="cap" />
 </item_types>
 */
class ItemTypeDataSet {
    Long id
    String type

    @Override
    public String toString(){
        return "itemTypeDataSet[id:$id, type:$type]"
    }
}
