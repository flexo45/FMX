package gamemanager

import gamedata.CardDataManager
import gamedata.GameDataManager
import decks.DoorsDeck
import decks.GoldsDeck
import fightmanager.Fight
import interfaces.ICard
import interfaces.IClass
import interfaces.IRace
import log.Log
import npcmanager.NPCTurn
import ui.gametable.ActionListManager
import ui.gametable.GameTableEvents
import ui.gametable.GameTableView

class GameProcessor {

    private static Log logger = new Log(GameProcessor.class.name)

    private static GameProcessor instance
    public static GameProcessor getInstance(){
        if(instance == null) instance = new GameProcessor()
        return instance
    }
    private GameProcessor(){}

    private Game game
    private Player currentPlayer

    public GameTableEvents view
    public volatile Integer round = 1
    public ICard cardForUsing
    public Object targetForUsing
    public Fight fighting

    public Game getGame() { return game }

    public Integer getRound() { return round }

    public Player getCurrentPlayer() { return currentPlayer }

    public Player getPlayer(){
        return game.playerList.find{
            !it.npc
        }
    }

    public void nextTurn(){
        setNextPlayer()
        game.turn++

        round = 1

        view.gameInfoChangedNotify()

        if(currentPlayer.npc){
            view.actionChangedNotify(ActionListManager.OPPONENT_ROUND_BEGIN)
            new Thread(new NPCTurn()).start()
        }
        else {
            view.actionChangedNotify(ActionListManager.FIRST_ROUND_BEGIN)
        }

        logger.info("Next turn begin: $currentPlayer, turn: ${game.turn}")
    }

    public void loadGame(String gameName){
        this.game = GameDataManager.instance.getGame(gameName)

        currentPlayer = game.playerList.find{ !it.npc }

        GameTableView.initialize()

        view.playersChangedNotify()
        view.playerHandChangedNotify()
        view.gameInfoChangedNotify()
        view.equipmentChangedNotify()

        view.actionChangedNotify(ActionListManager.FIRST_ROUND_BEGIN)

        logger.info("Game '${game.gameName}' loaded successfuly")
    }

    public void setNextPlayer(){
        Player oldPlayer = currentPlayer
        Integer idx = game.playerList.indexOf(currentPlayer)
        if(idx + 1 == game.playerList.size()){ idx = 0 }
        else { ++idx }
        currentPlayer = game.playerList.get(idx)

        logger.info("Next player was selected: $currentPlayer, prev: $oldPlayer")
    }

    public void startNewGame(List<Player> players){
        List<ICard> newDoorsDeck = []
        List<ICard> newGoldsDeck = []

        def d_race = CardDataManager.instance.getCard(10000)

        logger.debug("startNewGame: default race=$d_race")

        def d_class = CardDataManager.instance.getCard(20000)

        logger.debug("startNewGame: default class=$d_class")

        def game_name = ""
        players.each {game_name += it.name + ' '}

        CardDataManager.instance.getDoorsListId().each {
            if(it != 10000 && it != 20000){
                int i = CardDataManager.instance.getCardCountInSet(it)
                ICard card = CardDataManager.instance.getCard(it)
                (0..i).each {
                    newDoorsDeck.add(card)
                }
            }
        }

        logger.debug("startNewGame: doorDeck=$newDoorsDeck")

        CardDataManager.instance.getGoldsListId().each {
            int i = CardDataManager.instance.getCardCountInSet(it)
            ICard card = CardDataManager.instance.getCard(it)
            (0..i).each {
                newGoldsDeck.add(card)
            }
        }

        logger.debug("startNewGame: goldDeck=$newGoldsDeck")

        players.each {
            p ->
                p.c1ass = (IClass)d_class
                p.race = (IRace)d_race
        }

        game = new Game(playerList: players,
                defaultClass: (IClass)d_class,
                defaultRace: (IRace)d_race,
                gameName: game_name,
                doors: new DoorsDeck(newDoorsDeck, []),
                golds: new GoldsDeck(newGoldsDeck, []))

        game.doors.shuffle()

        logger.debug("startNewGame: shuffled doorsDeck=${game.doors}")

        game.golds.shuffle()

        logger.debug("startNewGame: shuffled goldDeck=${game.golds}")

        game.playerList.each {
            p ->
            (0..3).each {
                p.hand.add(game.doors.getNextCard())
            }
            (0..1).each {
                p.hand.add(game.golds.getNextCard())
            }
                logger.debug("startNewGame: $p get hand=${p.hand}")
        }

        currentPlayer = game.playerList.find{ !it.npc }

        logger.debug("startNewGame: found player $currentPlayer")

        GameTableView.initialize()

        view.playersChangedNotify()
        view.playerHandChangedNotify()

        view.actionChangedNotify(ActionListManager.FIRST_ROUND_BEGIN)

        logger.info("Starting new game ${game.gameName}")
    }
}
