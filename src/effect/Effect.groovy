package effect

import itemmanager.ItemSize
import itemmanager.ItemType

class Effect {
    int id
    Target target
    Property property
    Action action
    ItemType itemType
    ItemSize itemSize

    @Override
    public String toString(){
        return "effect[$action the ${itemSize==null?"":itemSize} " +
                "$property ${itemType==null?"":itemType} on $target]"
    }
}
