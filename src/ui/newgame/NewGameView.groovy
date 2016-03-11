package ui.newgame

import ui.MainWindowView
import groovy.swing.SwingBuilder

import javax.swing.BoxLayout
import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JTable
import javax.swing.JTextField
import java.beans.PropertyChangeListener

/**
 * Created by m.guriev on 05.02.2016.
 */
class NewGameView {
    static SwingBuilder sb = new SwingBuilder()

    static def initialize(){
        def content = sb.panel{
            boxLayout(axis: BoxLayout.Y_AXIS)

            panel(maximumSize: [600, 80]){
                boxLayout(axis: BoxLayout.LINE_AXIS)

                button(text: "Add", actionPerformed: { NewGameModel.instance.addPlayer() })

                button(text: "Remove", actionPerformed: { NewGameModel.instance.removePlayer() })

                button(text: "Start", actionPerformed: { NewGameModel.instance.startGame() })
            }
            panel(maximumSize: [600, 80]){
                gridLayout(rows: 3, cols: 2)

                label(text: "Name")
                tf_name = textField()

                label(text: "Sex")
                cb_sex = comboBox(items: ["male", "female"])

                label(text: "Is Player")
                chb_isPlayer = checkBox(enabled: false, selected: true)
            }
            scrollPane{
                t_players_list = table{
                    tableModel(id: "players_list", list: NewGameModel.instance.players_list){
                        p ->
                            propertyColumn(header: "Name", propertyName: "name")
                            propertyColumn(header: "Sex", propertyName: "sex")
                    }
                }
                NewGameModel.instance.players_list.addPropertyChangeListener({ e ->
                    players_list.fireTableDataChanged()
                } as PropertyChangeListener )
            }
        }

        MainWindowView.instance.setContent(content)
        MainWindowView.instance.setSize(480, 320)
        MainWindowView.instance.setTitle("%GAME_NAME% - New Game")
    }

    static JTextField tf_name
    static JComboBox cb_sex
    static JTable t_players_list
    static JCheckBox chb_isPlayer
}
