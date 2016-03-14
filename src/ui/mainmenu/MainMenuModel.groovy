package ui.mainmenu

import ui.loadgame.LoadGameView
import ui.MainWindowView
import ui.newgame.NewGameView

class MainMenuModel {
    protected static void loadGame(){
        LoadGameView.initialize()
    }
    protected static void newGame(){
        NewGameView.initialize()
    }
    protected static void exitGame(){
        MainWindowView.instance.close()
    }
}
