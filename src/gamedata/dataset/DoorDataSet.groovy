package gamedata.dataset

import interfaces.ICard

/*
* <doors>
		<race>
			<card id="10000" name="Human" effect="" info=""/>
		</race>
		<professon>
			<card id="20000" name="Commoner" effect="" info=""/>
		</professon>
		<monster>
			<card id="30000" name="Goblin" level="1" effect="" info=""/>
		</monster>
		<curse>
			<card id="40000" name="Change race" effect="" info=""/>
		</curse>
		<spell>
		</spell>
		<event>
		</event>
	</doors>*/
class DoorDataSet {
    Long id
    String name
    Class type
    Long effect
    String info
    int count
    int level
    Long obscenity
    int obscenity_value
    int gold
    int add_level
    int power

    @Override
    public String toString(){
        return "doorsDataSet[id:$id, name:$name, type:$type, effect:$effect, info:$info, count:$count, level:$level]"
    }
}
