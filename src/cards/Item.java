package cards;

import effect.Effect;
import itemmanager.ItemSize;
import itemmanager.ItemType;

/**
 * Created by m.guriev on 18.01.2016.
 */
public class Item extends BaseCard {

    public Item(){}

    private ItemSize size;
    private ItemType type;
    private Effect effect;
    private int power;

    public ItemSize getSize(){return size;}
    public ItemType getType(){return type;}
    public Effect getEffect(){return effect;}
    public int getPower(){return power;}

    public void setSize(ItemSize value) {size = value;}
    public void setType(ItemType value) {type = value;}
    public void setEffect(Effect value) {effect = value;}
    public void setPower(int value) {power = value;}

    public void use(){

    }
    @Override
    public String toString(){
        return getClass().getTypeName() + ":" + getName();
    }

}
