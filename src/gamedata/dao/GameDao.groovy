package gamedata.dao

import gamedata.GameDataManager
import gamedata.dataset.GameDataSet
import log.Log

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

class GameDao {

    public GameDataSet getById(String id){
        Node config = GameDataManager.instance.getConfig()

        Node game = (Node)config.children().find{ Node n -> n.attribute('id').equals(id) }

        if(game == null){
            Log.print(this, "ERROR: game not found by ID: $id")
            return null
        }
        else {
            GameDataSet gameDataSet = new GameDataSet(id: id, turn: Long.valueOf(game.attribute('turn').toString()))

            gameDataSet.doors = []

            String value = (game.find{Node n -> n.name().equals('doors')} as Node)?.value()?:""
            value = value.minus('[').minus(']')
            value.split(";").each {
                if(!it.isEmpty()){
                    gameDataSet.doors.add(Long.valueOf(it))
                }

            }

            gameDataSet.doors_drop = []

            value = (game.find{Node n -> n.name().equals('doors_drop')} as Node)?.value()?:""
            value = value.minus('[').minus(']')
            value.split(";").each {
                if(!it.isEmpty()){gameDataSet.doors_drop.add(Long.valueOf(it))}

            }

            gameDataSet.golds = []

            value = (game.find{Node n -> n.name().equals('golds')} as Node)?.value()?:""
            value = value.minus('[').minus(']')
            value.split(";").each {
                if(!it.isEmpty()){gameDataSet.golds.add(Long.valueOf(it))}

            }

            gameDataSet.golds_drop = []

            value = (game.find{Node n -> n.name().equals('golds_drop')} as Node)?.value()?:""
            value = value.minus('[').minus(']')
            value.split(";").each {
                if(!it.isEmpty()){gameDataSet.golds_drop.add(Long.valueOf(it))}

            }

            return gameDataSet
        }
    }

    public List<String> getIdList(){
        Node config = GameDataManager.instance.getConfig()

        List<String> list = []

        config.children().each {
            Node n ->
                if(n.name().equals('game')){ list.add(n.attribute('id').toString()) }
        }

        return list
    }
}
