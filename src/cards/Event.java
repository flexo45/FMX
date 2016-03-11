package cards;

/**
 * Created by m.guriev on 18.01.2016.
 */
public class Event extends BaseCard {

    public Event() {}
    @Override
    public String toString(){
        return getClass().getTypeName() + ":" + getName();
    }
}
