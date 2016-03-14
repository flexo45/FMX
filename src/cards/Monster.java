package cards;

import effect.Effect;
import effect.EffectManager;
import gamemanager.Player;

public class Monster extends BaseCard {

    public Monster(){
    }

    private Integer level;
    private Integer gold;
    private Effect obscenity;
    private int obscenity_value;
    private int add_level;

    public Integer getLevel(){
        return level;
    }
    public Integer getAdd_level(){
        return add_level;
    }
    public Integer getGold() { return gold; }
    public Effect getObscenity() { return obscenity; }
    public Integer getObscenity_value() { return obscenity_value; }


    public void setLevel(int level) {this.level = level;}
    public void setAdd_level(int add_level) {this.add_level = add_level;}
    public void setGold(int gold) {this.gold = gold;}
    public void setObscenity(Effect obscenity) {this.obscenity = obscenity;}
    public void setObscenity_value(int obscenity_value) {this.obscenity_value = obscenity_value;}


    public void obscenity(Player targetPlayer) throws Exception{
        EffectManager.processEffect(targetPlayer, obscenity, obscenity_value);
    }

    @Override
    public String toString(){
        return "monster[name:" + getName() + "]";
    }

}
