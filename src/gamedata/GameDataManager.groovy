package gamedata

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
import interfaces.ICard
import interfaces.IClass
import interfaces.IRace
import log.Log

class GameDataManager {

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
    }

    public Node getConfig(){
        checkConfig()
        return config
    }

    public List<String> getGameList(){
        return gameDao.getIdList()
    }

    public Game getGame(String gameId){

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

        return game
    }

    public String getCardSetPath(){
        return settingsDao.getCardSetPath()
    }

    public void saveGame(Game game){
/*
        checkConfig()

        Node n_game = (Node)config.find{ it.name().equals('game') && it.attribute('id').equals(game.gameName) }
        if(n_game == null){
            n_game = config.appendNode('game', [id:game.gameName, turn:game.turn])
        }
        else {
            n_game.attributes().put('turn', game.turn)
            n_game.children().clear()
        }
        Node players = n_game.appendNode('players')
        game.playerList.each {
            Node player = players.appendNode('player', [name:it.name, sex:it.sex ? '1' : '0', level:it.level.toString()
                                      , class:CardManager.instance.getIdOfCard((ICard)it.c1ass)
                                      , race:CardManager.instance.getIdOfCard((ICard)it.race)
                                      , npc: it.npc ? '1' : '0'])

            def v_hand = new StringBuilder()
            it.hand.each {
                c ->
                v_hand.append(CardManager.instance.getIdOfCard(c) + ';')
            }

            player.appendNode('hand').setValue(v_hand)
        }

        def v_cards = new StringBuilder()
        game.doors.deck.each {
            v_cards.append(CardManager.instance.getIdOfCard(it) + ';')
        }
        n_game.appendNode('doors').setValue(v_cards)

        v_cards = new StringBuilder()
        game.doors.drop.each {
            v_cards.append(CardManager.instance.getIdOfCard(it) + ';')
        }
        n_game.appendNode('doors_drop').setValue(v_cards)

        StringWriter writer = new StringWriter()
        XmlNodePrinter printer = new XmlNodePrinter(new PrintWriter(writer))
        printer.preserveWhitespace = true
        printer.print(config)
        updateConfig(writer.toString())

        Log.print(this, "game saved success")*/
    }

    private void checkConfig(){
        if(gameDataDao.isConfigOutdate()){
            config = gameDataDao.getGameData(CONFIG_FILE_NAME)
        }
    }
}
