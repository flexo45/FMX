package gamedata.dataset

/**
 * <item_size>
 <size id="10" type="small"/>
 <size id="11" type="big"/>
 </item_size>
 */
class ItemSizeDataSet {
    Long id
    String type

    @Override
    public String toString(){
        return "itemSizeDataSet[id:$id, type:$type]"
    }
}
