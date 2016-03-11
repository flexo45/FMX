package ui.loadgame

import ui.MainWindowView
import groovy.swing.SwingBuilder

import javax.swing.BoxLayout
import javax.swing.JList

/**
 * Created by m.guriev on 05.02.2016.
 */
class LoadGameView {
    static SwingBuilder sb = new SwingBuilder()

    static def initialize(){
        def content = sb.panel{
            boxLayout(axis: BoxLayout.Y_AXIS)
            panel(maximumSize: [600, 80]){
                boxLayout(axis: BoxLayout.LINE_AXIS)
                button(text: "Load", actionPerformed: { LoadGameModel.loadGame() })
            }
            panel(maximumSize: [600, 80]){
                boxLayout(axis: BoxLayout.LINE_AXIS)
                label(text: "Select game:")
            }
            scrollPane{
                g_list = list(items: LoadGameModel.gameList){

                }
            }
        }

        MainWindowView.instance.setContent(content)
        MainWindowView.instance.setSize(320, 240)
        MainWindowView.instance.setTitle("%GAME_NAME% - Load Game Menu")
    }

    static JList<String> g_list
}
