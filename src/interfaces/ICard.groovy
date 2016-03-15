package interfaces

import effect.Effect

public interface ICard {
    Long getId()
    void setId(Long value)
    String getName()
    String getInfo()
    Effect getEffect()
    void setName(String name)
    void setInfo(String info)
    void setEffect(Effect effect)
}