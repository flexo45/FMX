package gamedata.dao

import gamedata.GameDataManager
import gamedata.dataset.PlayerDataSet
import log.Log

/*<player name="player_A" sex="0" level="1" class="20002" race="10001" npc="0">
        <hand>30005;30002;30006;20004;80001;</hand>*/

class PlayerDao {

    public List<PlayerDataSet> getPlayerList(String gameId){

        Node config = GameDataManager.instance.getConfig()

        Node game = (Node)config.children().find{ Node n -> n.attribute('id').equals(gameId) }

        if(game == null){
            Log.print(this, "ERROR: game not found by ID: $gameId")
            return null
        }
        else {
            Node players = (Node)game.children().find{ Node n -> n.name().equals('players') }
            if(players == null){
                Log.print(this, "ERROR: players not found in game ID: $gameId")
                return null
            }
            else {
                List<PlayerDataSet> playerDataSetList = []

                players.children().each {
                    Node p ->

                        PlayerDataSet playerDataSet = new PlayerDataSet(name: p.attribute('name')
                                , sex: p.attribute('sex').equals('1')
                                , level: Integer.valueOf(p.attribute('level').toString())
                                , c1ass: Long.valueOf(p.attribute('class').toString())
                                , race: Long.valueOf(p.attribute('race').toString()), npc: p.attribute('npc').equals('1'))

                        playerDataSet.hand = []

                        String value = ((Node)p.find{Node n -> n.name().equals('hand')})?.value()?:""
                        value = value.minus('[').minus(']')
                        value.split(";").each {
                            playerDataSet.hand.add(Long.valueOf(it))
                        }

                        playerDataSetList.add(playerDataSet)
                }

                Log.print(this, "DEBUG: successful loaded: $playerDataSetList")

                return playerDataSetList
            }
        }
    }

}
