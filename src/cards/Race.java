package cards;

import interfaces.IRace;

/**
 * Created by m.guriev on 18.01.2016.
 */
public class Race extends BaseCard implements IRace {

    public Race() {}

    @Override
    public String toString(){
        return getClass().getTypeName() + ":" + getName();
    }
}
