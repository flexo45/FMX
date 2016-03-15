package gamedata.dao

import gamedata.GameDataManager
import gamedata.dataset.GameDataSet
import gamemanager.Game
import gamemanager.Player
import interfaces.ICard
import log.Log

class GameDao {

    public GameDataSet getById(String id){
        Node config = GameDataManager.instance.getConfig()

        Node game = (Node)config.children().find{ Node n -> n.attribute('id').equals(id) }

        if(game == null){
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

    public void addGame(Game game_data){
        Node config = GameDataManager.instance.getConfig()

        String node_value = ""

        Node game = (Node)config.children().find{ Node n -> n.attribute('id').equals(game_data.gameName) }

        if(game == null){
            game = config.appendNode("game", [id: game_data.gameName, turn: game_data.turn]) //TODO bind card set
        }
        else {
            game.children().clear()
            game.attributes().put("turn", game_data.turn)
        }

        Node players = game.appendNode("players")

        game_data.playerList.each {
            Node player = players.appendNode("player", [name: it.name
                                          , sex: it.sex ? "1" : "0"
                                          , level: it.level
                                          , class: (it.c1ass as ICard).id
                                          , race: (it.race as ICard).id
                                          , npc: it.npc ? "1" : "0"])

            it.hand.each {
                node_value = appendValue(node_value, it.id.toString())
            }

            players.appendNode("hand").setValue(node_value)

            node_value = ""
            it.equipment.getAllItems().each {
                node_value = appendValue(node_value, it.id.toString())
            }

            players.appendNode("equipments").setValue(node_value)

            players.append(player)
        }

        node_value = ""
        game_data.doors.deck.each {
            node_value = appendValue(node_value, it.id.toString())
        }

        game.appendNode("doors").setValue(node_value)

        node_value = ""
        game_data.doors.drop.each {
            node_value = appendValue(node_value, it.id.toString())
        }

        game.appendNode("doors_drop").setValue(node_value)

        node_value = ""
        game_data.golds.deck.each {
            node_value = appendValue(node_value, it.id.toString())
        }

        game.appendNode("golds").setValue(node_value)

        node_value = ""
        game_data.golds.drop.each {
            node_value = appendValue(node_value, it.id.toString())
        }

        game.appendNode("golds_drop").setValue(node_value)
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

    private String appendValue(String text, String value){
        return text + value + ";"
    }
}
