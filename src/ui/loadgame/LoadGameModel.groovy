package ui.loadgame

import dao.GameManagerDao
import gamemanager.GameProcessor

/**
 * Created by m.guriev on 05.02.2016.
 */
class LoadGameModel {
    static void loadGame(){
        GameProcessor.instance.loadGame(LoadGameView.g_list.selectedValue)
    }

    static List<String> gameList = GameManagerDao.getGameList()
}
