package cards;

import effect.EffectManager;
import gamemanager.Player;

public class Curse extends BaseCard {

    private int power;

    public Curse()  { }

    public void setPower(int value) {power = value;}
    public int getPower(){return power;}

    public void execute(Player target) throws Exception{
        EffectManager.processEffect(target, effect, power);
    }
    @Override
    public String toString(){
        return "curse[name:" + getName() + "]";
    }
}
