package cards;

public class Curse extends BaseCard {

    public Curse()  { }
    public void execute(){}
    @Override
    public String toString(){
        return "curse[name:" + getName() + "]";
    }
}
