package ui.gametable

import cards.Item
import cards.Profession
import cards.Race
import cards.Spell
import gamemanager.GameProcessor
import interfaces.ICard
import ui.MainWindowView
import groovy.swing.SwingBuilder

import javax.swing.BoxLayout
import javax.swing.JDialog
import javax.swing.JTable
import java.awt.Dimension

/**
 * Created by m.guriev on 11.02.2016.
 */
class SelectCardPopup {

    static void initialize(Class<?> cardClass){
        initializeItemList(cardClass)

        SwingBuilder sb = new SwingBuilder()
        popup = sb.optionPane().createDialog(MainWindowView.instance.getFrame(), "Select ${cardClass.simpleName}")

        popup.contentPane.clear()
        popup.setMinimumSize(new Dimension(240, 120))
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
                GameProcessor.instance.cardForUsing = (ICard)it.get('card')
                GameProcessor.instance.currentPlayer.hand.remove((ICard)it.get('card'))
            }
        }
        popup.dispose()
    }

    private static void initializeItemList(Class<?> cardClass){

            list.clear()

        switch (cardClass){
            case Race.class:
                GameProcessor.instance.getPlayer().hand.each {
                    if(it.class.equals(Race.class)){
                        list.add([name:it.name, info:it.info, card:it])
                    }
                }
                break
            case Profession.class:
                GameProcessor.instance.getPlayer().hand.each {
                    if(it.class.equals(Profession.class)){
                        list.add([name:it.name, info:it.info, card:it])
                    }
                }
                break
            case Spell.class:
                GameProcessor.instance.getPlayer().hand.each {
                    if(it.class.equals(Spell.class)){
                        list.add([name:it.name, info:it.info, card:it])
                    }
                }
                break
            case Item.class:
                GameProcessor.instance.getPlayer().hand.each {
                    if(it.class.equals(Item.class)){
                        list.add([name:it.name, info:it.info, card:it])
                    }
                }
                break

        }
    }
}
