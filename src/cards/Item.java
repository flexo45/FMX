package cards;

import effect.Effect;
import itemmanager.ItemSize;
import itemmanager.ItemType;

public class Item extends BaseCard {

    public Item(){}

    private ItemSize size;
    private ItemType type;
    private int power;
    private int cell;

    public ItemSize getSize(){return size;}
    public ItemType getType(){return type;}
    public int getPower(){return power;}
    public int getCell(){return cell;}

    public void setSize(ItemSize value) {size = value;}
    public void setType(ItemType value) {type = value;}
    public void setPower(int value) {power = value;}
    public void setCell(int value) {cell = value;}

    public void use(){

    }

    @Override
    public String toString(){
        return "item[name:" + getName() + ", type:" + getType() + "]";
    }

}
