package cards;

import interfaces.IRace;

public class Race extends BaseCard implements IRace {

    public Race() {}

    @Override
    public String toString(){
        return "race[name:" + getName() + "]";
    }
}
