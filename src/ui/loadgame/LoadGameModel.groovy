package ui.loadgame

import gamedata.GameDataManager
import gamemanager.GameProcessor

class LoadGameModel {
    static void loadGame(){
        GameProcessor.instance.loadGame(LoadGameView.g_list.selectedValue)
    }

    static List<String> gameList = GameDataManager.instance.getGameList()
}
