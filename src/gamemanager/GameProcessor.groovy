package gamemanager

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
            new Thread(new NPCTurn()).start()
        }
        else {
            view.actionChangedNotify(ActionListManager.FIRST_ROUND_BEGIN)
        }

        Log.print(this, "next turn processed: {next_player: $currentPlayer, turn: ${game.turn}")

        //TODO CONCURENT EXCEPTION
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

        Log.print(this, "load game processed: {name: ${this.game.gameName}, turn: ${this.game.turn}" +
                "doors: ${this.game.doors.deck}, golds: ${this.game.golds.deck} }")
    }

    public void setNextPlayer(){
        Integer idx = game.playerList.indexOf(currentPlayer)
        if(idx + 1 == game.playerList.size()){ idx = 0 }
        else { ++idx }
        currentPlayer = game.playerList.get(idx)
    }

    public void startNewGame(List<Player> players){
        List<ICard> newDoorsDeck = []
        List<ICard> newGoldsDeck = []

        def d_race
        def d_class
        def game_name = ""
        players.each {game_name += it.name + ' '}
/*
        CardManager.instance.doorSet.each {
            c ->
            if((String)c.get('id') == '10000'){
                d_race = c.get('card')
            }
            else if((String)c.get('id') == '20000'){
                d_class = c.get('card')
            }
            else{
                Integer i = Integer.parseInt((String)c.get('count'))
                (0..i).each {
                    newDoorsDeck.add((ICard)c.get('card'))
                }
            }
        }
*/
        Log.print(this, "new_game_processor, default race: $d_race")
        Log.print(this, "new_game_processor, default class: $d_class")
        Log.print(this, "new_game_processor, door deck: $newDoorsDeck")
/*
        CardManager.instance.goldSet.each {
            c ->
                Integer i = Integer.parseInt((String)c.get('count'))
                (0..i).each {
                    newGoldsDeck.add((ICard)c.get('card'))
                }
        }
*/
        Log.print(this, "gold deck: $newGoldsDeck")

        players.each {
            p ->
                p.c1ass = (IClass)d_class
                p.race = (IRace)d_race
        }

        game = new Game(playerList: players,
                defaultClass: d_class,
                defaultRace: d_race,
                gameName: game_name,
                doors: new DoorsDeck(newDoorsDeck, []),
                golds: new GoldsDeck(newGoldsDeck, []))

        game.doors.shuffle()
        game.golds.shuffle()

        Log.print(this, "new_game_processor, door deck shuffled: ${game.doors}")
        Log.print(this, "new_game_processor, gold deck shuffled: ${game.golds}")

        game.playerList.each {
            p ->
            (0..3).each {
                p.hand.add(game.doors.getNextCard())
            }
            (0..1).each {
                p.hand.add(game.golds.getNextCard())
            }
                Log.print(this, "new_game_processor, player ${p.name} hand: ${p.hand}")
        }



        currentPlayer = game.playerList.find{ !it.npc }

        GameTableView.initialize()

        view.playersChangedNotify()
        view.playerHandChangedNotify()

        view.actionChangedNotify(ActionListManager.FIRST_ROUND_BEGIN)

        Log.print(this, "new_game_processor, success")
    }
}
