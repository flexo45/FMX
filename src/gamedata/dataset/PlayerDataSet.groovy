package gamedata.dataset
/*<player name="player_A" sex="0" level="1" class="20002" race="10001" npc="0">
        <hand>30005;30002;30006;20004;80001;</hand>*/

class PlayerDataSet {

    String name
    boolean sex
    int level
    long c1ass
    long race
    boolean npc
    List<Long> hand

    @Override
    public String toString(){
        return "playerDataSet[name:$name, sex:$sex, level:$level, class:$c1ass, race:$race, npc:$npc, hand:$hand]"
    }
}
