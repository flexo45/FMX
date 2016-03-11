package cards;

/**
 * Created by m.guriev on 18.01.2016.
 */
public class Curse extends BaseCard {

    public Curse()  { }
    public void execute(){}
    @Override
    public String toString(){
        return getClass().getTypeName() + ":" + getName();
    }
}
