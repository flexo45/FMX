package cards;

public class Spell extends BaseCard {

    public Spell() {}

    public void cast(){

    }

    @Override
    public String toString(){
        return "spell[name:" + getName() + "]";
    }
}
