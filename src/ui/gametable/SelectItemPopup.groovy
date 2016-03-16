package ui.gametable

import cards.Item
import cards.Monster
import cards.Profession
import cards.Race
import cards.Spell
import gamemanager.GameProcessor
import groovy.swing.SwingBuilder
import interfaces.ICard
import itemmanager.ItemSize
import itemmanager.ItemType
import ui.MainWindowView

import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JTable
import java.awt.Dimension

class SelectItemPopup {

    static JButton button

    static void initialize(List<Item> items){

        initializeItemList(items)

        SwingBuilder sb = new SwingBuilder()
        popup = sb.optionPane().createDialog(MainWindowView.instance.getFrame()
                , "Select item")

        popup.contentPane.clear()
        popup.setMinimumSize(new Dimension(120, 120))
        def view = sb.panel{
            boxLayout(axis: BoxLayout.Y_AXIS)
            scrollPane(){
                table = table(mousePressed: {
                    button.setEnabled(true)
                }){
                    tableModel(list: list){
                        p ->
                            propertyColumn(header: 'Name', propertyName: 'name');
                            propertyColumn(header: 'Info', propertyName: 'info');
                    }
                }
            }
            panel(maximumSize: [4000, 80]){
                button = button(text: "Select", enabled: false, actionPerformed: { select() })
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
                GameProcessor.instance.selectedItem = (Item)it.get('card')
            }
        }
        popup.dispose()
    }

    private static void initializeItemList(List<Item> itemList){

        list.clear()

        itemList.each {
            list.add([name: it.name, info: it.info, card: it])
        }
    }
}
