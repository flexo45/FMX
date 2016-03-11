package cards;

public class Monster extends BaseCard {

    public Monster(){
    }

    private Integer level;
    private Integer gold;

    public Integer getLevel(){
        return level;
    }
    public Integer getGold() { return gold; }

    public Object obscenity(){ return 0; }
    @Override
    public String toString(){
        return getClass().getTypeName() + ":" + getName();
    }

}
