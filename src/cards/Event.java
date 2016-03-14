package cards;

public class Event extends BaseCard {

    public Event() {}
    @Override
    public String toString(){
        return "event[name:" + getName() + "]";
    }
}
