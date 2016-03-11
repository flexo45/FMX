package cards;

import interfaces.IClass;

/**
 * Created by m.guriev on 18.01.2016.
 */
public class Profession extends BaseCard implements IClass {

    public Profession() {}

    @Override
    public String toString(){
        return getClass().getTypeName() + ":" + getName();
    }

}
