package cards;

import effect.Effect;
import interfaces.ICard;

public abstract class BaseCard implements ICard {

    public BaseCard(){
    }

    String name;
    String info;
    Effect effect;

    @Override
    public void setName(String name){ this.name = name; }

    @Override
    public void setInfo(String info){ this.info = info; }

    @Override
    public void setEffect(Effect effect) {this.effect = effect;}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInfo(){
        return info;
    }

    @Override
    public Effect getEffect() {return effect;}

}
