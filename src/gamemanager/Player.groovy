package gamemanager

import equipment.Equipment
import interfaces.ICard
import interfaces.IClass
import interfaces.IRace

class Player {
    String name
    Boolean sex
    IRace race = null
    IClass c1ass = null
    List<ICard> hand = []
    Boolean npc = false
    Integer level = 1
    Equipment equipment = new Equipment();
}
