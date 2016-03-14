package gamedata.dataset

/**
 * <equip_map>
 <item place="head" type="104" cell="1"/>
 <item place="hand" type="101" cell="2"/>
 <item place="body" type="102" cell="1" />
 <item place="legs" type="103" cell="1" />
 </equip_map>
 */
class EquipMapDataSet {
    String place
    Long type
    int cell

    @Override
    public String toString(){
        return "equipMapDataSet[place:$place, type:$type, cell:$cell]"
    }
}
