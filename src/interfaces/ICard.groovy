package interfaces

import effect.Effect

public interface ICard {
    String getName()
    String getInfo()
    Effect getEffect()
    void setName(String name)
    void setInfo(String info)
    void setEffect(Effect effect)
}