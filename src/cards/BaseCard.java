package cards;

import interfaces.ICard;

public abstract class BaseCard implements ICard {

    public BaseCard(){
    }

    String name;
    String info;

    public void setName(String name){ this.name = name; }
    public void setInfo(String info){ this.info = info; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo(){
        return info;
    }
}
