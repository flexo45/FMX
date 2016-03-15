package ui.gametable

import cards.Item
import cards.Monster
import cards.Profession
import cards.Race
import cards.Spell
import effect.Target
import gamemanager.GameProcessor
import gamemanager.Player
import groovy.swing.SwingBuilder
import interfaces.ICard
import ui.MainWindowView

import javax.swing.*
import java.awt.*
import java.util.List

class SelectTargetPopup {

    static void initialize(Target target){
        initializeItemList(target)

        SwingBuilder sb = new SwingBuilder()
        popup = sb.optionPane().createDialog(MainWindowView.instance.getFrame(), "Select ${target}")

        popup.contentPane.clear()
        popup.setMinimumSize(new Dimension(120, 120))
        def view = sb.panel{
            boxLayout(axis: BoxLayout.Y_AXIS)
            scrollPane(){
                table = table{
                    tableModel(list: list){
                        p ->
                            propertyColumn(header: 'Name', propertyName: 'name');
                            propertyColumn(header: 'Info', propertyName: 'info');
                    }
                }
            }
            panel(maximumSize: [4000, 80]){
                button(text: "Select", actionPerformed: { select() })
            }
        }
        popup.contentPane.add(view)
        popup.pack()
        popup.show(true)
    }

    private static List<Map> list = []
    private static JTable table
    private static JDialog popup

    private static void select(){
        list.each {
            if(it.get('name').equals(table.getValueAt(table.selectedRow, 0))){
                GameProcessor.instance.targetForUsing = it.get('target')
            }
        }
        popup.dispose()
    }

    private static void initializeItemList(Target targetClass){

            list.clear()

        switch (targetClass){
            case Target.PLAYER:
                GameProcessor.instance.game.playerList.each {
                    list.add([name:it.name, info:it.toString(), target:it])
                }
                break
            case Target.MONSTER:
                def it = GameProcessor.instance.fighting.monster
                list.add([name:it.name, info:it.info, target:it])
                break
        }
    }
}
