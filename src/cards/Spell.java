package cards;

import effect.EffectManager;
import gamemanager.GameProcessor;
import gamemanager.Player;
import log.Log;

public class Spell extends BaseCard {

    public Spell() {}

    private int power;

    public void setPower(int power){this.power = power;}
    public int getPower() {return power;}

    public void cast(Object target) throws Exception{
        if(Player.class.isInstance(target)){
            EffectManager.processEffect(target, effect, power);
        }
        else if(Monster.class.isInstance(target)){
            if(GameProcessor.getInstance().fighting != null){
                EffectManager.processEffect(target, effect, power);
            }
            else {
                throw new Exception("Incorrect target " + target + " for effect " + effect);
            }
        }
    }

    @Override
    public String toString(){
        return "spell[name:" + getName() + "]";
    }
}
