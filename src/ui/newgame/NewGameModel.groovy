package ui.newgame

import gamemanager.GameProcessor
import gamemanager.Player
import ui.MainWindowView
import groovy.beans.Bindable

/**
 * Created by m.guriev on 05.02.2016.
 */
class NewGameModel {

    private static NewGameModel instance
    public static NewGameModel getInstance(){
        if(instance == null) instance = new NewGameModel()
        return instance
    }

    private NewGameModel() {}

    @Bindable
    ObservableList players_list = []

    public void startGame(){
        List<Player> players = []
        players_list.each {
            Map p ->
                players.add(new Player(name: p.get('name'), sex: p.get('sex').equals('male'), npc: p.get('npc')))
        }
        GameProcessor.instance.startNewGame(players)
    }

    public void addPlayer(){
        String name = NewGameView.tf_name.text
        String sex = NewGameView.cb_sex.selectedItem
        Boolean npc = !NewGameView.chb_isPlayer.selected
        if(name == "" || sex == ""){
            MainWindowView.instance.exception("Name or Sex is empty")
        }
        else {
            players_list.add([name: name, sex: sex, npc: npc])
        }
        if(NewGameView.chb_isPlayer.selected){
            NewGameView.chb_isPlayer.selected = false
            NewGameView.chb_isPlayer.enabled = false
        }
        NewGameView.tf_name.text = ""
    }

    public void removePlayer(){
        players_list.each {
            Map p ->
                if(p.get('name')
                        .equals(NewGameView.t_players_list.getValueAt(NewGameView.t_players_list.selectedRow, 0))){
                    if(!p.get('npc')){
                        NewGameView.chb_isPlayer.selected = true
                        NewGameView.chb_isPlayer.enabled = false
                    }
                }
        }
        players_list.remove(NewGameView.t_players_list.selectedRow)
    }
}
