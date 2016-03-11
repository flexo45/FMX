package ui.mainmenu

import ui.MainWindowView
import groovy.swing.SwingBuilder

class MainMenuView {
    static SwingBuilder sb = new SwingBuilder()

    static def initialize(){
        def content = sb.panel{

            gridLayout(rows: 3, cols: 1)

            button(text: "New Game", actionPerformed: { MainMenuModel.newGame() })

            button(text: "Load Game", actionPerformed: { MainMenuModel.loadGame() })

            button(text: "Exit", actionPerformed: { MainMenuModel.exitGame() })
        }

        MainWindowView.instance.setContent(content)
        MainWindowView.instance.setSize(320, 240)
        MainWindowView.instance.setTitle("%GAME_NAME% - Main Menu")
        MainWindowView.instance.show()
    }
}
