package gamedata.dataset

class GameDataSet {

    /*<game id="player_A player_B player_C " turn="4">
    <players>
      <player name="player_A" sex="0" level="1" class="20002" race="10001" npc="0">
        <hand>30005;30002;30006;20004;80001;</hand>
      </player>
      <player name="player_B" sex="1" level="1" class="20001" race="10000" npc="1">
        <hand>20001;40004;20003;30007;10002;</hand>
      </player>
      <player name="player_C" sex="0" level="1" class="20000" race="10001" npc="1">
        <hand>10001;30009;30000;30003;10003;40002;</hand>
      </player>
    </players>
    <doors>30004;40003;20001;30008;30001;40001;</doors>
    <doors_drop></doors_drop>
    <golds>80002;80003;80004;</golds>
    <golds_drop></golds_drop>
  </game>*/

    String id
    Long turn
    List<PlayerDataSet> playerDataSetList
    List<Long> doors
    List<Long> doors_drop
    List<Long> golds
    List<Long> golds_drop

    @Override
    public String toString(){
        return "gameDataSet[id: $id, turn: $turn, players: $playerDataSetList, doors: $doors" +
                ", drop: $doors_drop, golds: $golds, drop: $golds_drop]"
    }
}
