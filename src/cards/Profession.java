package cards;

import interfaces.IClass;

public class Profession extends BaseCard implements IClass {

    public Profession() {}

    @Override
    public String toString(){
        return "profession[name:" + getName() + "]";
    }

}
