package gamedata

import ai.MainLogic
import cards.Profession
import cards.Race
import gamedata.dao.GameDao
import gamedata.dao.GameDataDao
import gamedata.dao.PlayerDao
import gamedata.dao.SettingsDao
import gamedata.dataset.GameDataSet
import decks.DoorsDeck
import decks.GoldsDeck
import gamemanager.Game
import gamemanager.Player
import groovy.xml.XmlUtil
import interfaces.ICard
import interfaces.IClass
import interfaces.IRace
import log.Log
import npcmanager.NPCTurn

class GameDataManager {

    private static Log logger = new Log(GameDataManager.class.name)

    private static GameDataManager instance

    private final String CONFIG_FILE_NAME = "game_data.xml"

    private Node config

    private GameDataDao gameDataDao

    private GameDao gameDao

    private PlayerDao playerDao

    private SettingsDao settingsDao

    public static GameDataManager getInstance(){
        if(instance == null) instance = new GameDataManager()
        return instance
    }
    private GameDataManager(){
        gameDataDao = new GameDataDao()
        gameDao = new GameDao()
        playerDao = new PlayerDao()
        settingsDao = new SettingsDao()

        config = gameDataDao.getGameData(CONFIG_FILE_NAME)

        logger.debug("Game config readed")
    }

    public Node getConfig(){
        checkConfig()
        return config
    }

    public List<String> getGameList(){
        return gameDao.getIdList()
    }

    public Game getGame(String gameId){

        try {
            GameDataSet gameDataSet = gameDao.getById(gameId)

            gameDataSet.playerDataSetList = playerDao.getPlayerList(gameId)

            IRace def_race = (Race)CardDataManager.instance.getCard(10000)

            IClass def_class = (Profession)CardDataManager.instance.getCard(20000)

            List<Player> playerList = []
            gameDataSet.playerDataSetList.each {
                Player player = new Player(name: it.name, npc: it.npc, sex: it.sex, level: it.level)

                it.hand.each {
                    id ->
                        player.hand.add(CardDataManager.instance.getCard(id))
                }

                player.c1ass = (IClass)CardDataManager.instance.getCard(it.c1ass)
                player.race = (IRace)CardDataManager.instance.getCard(it.race)

                if(player.npc){
                    player.ai = new MainLogic(player)
                }

                playerList.add(player)
            }

            List<ICard> doors = []
            gameDataSet.doors.each {
                doors.add(CardDataManager.instance.getCard(it))
            }

            List<ICard> doors_drop = []
            gameDataSet.doors_drop.each {
                doors_drop.add(CardDataManager.instance.getCard(it))
            }

            List<ICard> golds = []
            gameDataSet.golds.each {
                golds.add(CardDataManager.instance.getCard(it))
            }

            List<ICard> golds_drop = []
            gameDataSet.golds_drop.each {
                golds_drop.add(CardDataManager.instance.getCard(it))
            }

            Game game = new Game(gameName: gameDataSet.id
                    , playerList: playerList
                    , turn: gameDataSet.turn
                    , doors: new DoorsDeck(doors, doors_drop)
                    , golds: new GoldsDeck(golds, golds_drop)
                    , defaultRace: def_race
                    , defaultClass: def_class)

            logger.debug("${game.gameName} successful readed from config: $game")

            return game
        }
        catch (Exception e){
            logger.error("Read game failed", e)
            return null
        }
    }

    public String getCardSetPath(){
        try {
            return settingsDao.getCardSetPath()
        }
        catch (Exception e){
            logger.error("Get card set path failed", e)
            return null
        }
    }

    public void saveGame(Game game){

        try {
            gameDao.addGame(game)

            gameDataDao.setGameData(CONFIG_FILE_NAME, XmlUtil.serialize(config))
            logger.debug("Save success $game")
            logger.info("Game '${game.gameName}' successfuly saved")
        }
        catch (Exception e){
            logger.error("Save game failed", e)
        }
    }

    private void checkConfig(){
        if(gameDataDao.isConfigOutdate()){
            config = gameDataDao.getGameData(CONFIG_FILE_NAME)
            logger.debug("Game config updated")
        }
    }
}
