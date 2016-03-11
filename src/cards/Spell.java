package cards;

public class Spell extends BaseCard {

    public Spell() {}

    public void cast(){

    }

    @Override
    public String toString(){
        return getClass().getTypeName() + ":" + getName();
    }
}
