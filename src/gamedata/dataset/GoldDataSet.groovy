package gamedata.dataset

/**
 * <golds>
 <items>
 <card id="80001" name="Long sword" type="101" size="10" effect="4001" power="1"/>
 <card id="80002" name="Short bow" type="101" size="10" effect="4001" power="1"/>
 <card id="80003" name="Greate sword" type="101" size="11" cell="2" effect="4001" power="2"/>
 <card id="80004" name="Steel plate" type="102" size="11" effect="4001" power="3"/>
 <card id="80005" name="Leather jacket" type="102" size="10" effect="4001" power="2"/>
 <card id="80006" name="Chained cap" type="104" size="10" effect="4001" power="1"/>
 <card id="80006" name="Strong boots" type="103" size="10" effect="4001" power="1"/>
 </items>
 </golds>
 */
class GoldDataSet {

    Long id
    String name
    Class cardType
    Long type
    Long size
    Long effect
    int power
    int cell
    int count

    @Override
    public String toString(){
        return "goldDataSet[id:$id, name:$name, type:$type, size:$size" +
                ", effect:$effect, power:$power, cell:$cell, cardType:$cardType]"
    }

}
