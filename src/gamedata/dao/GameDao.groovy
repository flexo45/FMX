package gamedata.dao

import gamedata.GameDataManager
import gamedata.dataset.GameDataSet
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
