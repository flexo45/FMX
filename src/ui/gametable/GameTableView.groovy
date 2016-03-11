package ui.gametable

import gamemanager.GameProcessor
import ui.MainWindowView
import groovy.swing.SwingBuilder

import javax.swing.BoxLayout
import javax.swing.JSplitPane
import javax.swing.JTable
import javax.swing.border.LineBorder
import java.awt.Color
import java.beans.PropertyChangeListener

/**
 * Created by m.guriev on 08.02.2016.
 */
class GameTableView {
    static SwingBuilder sb = new SwingBuilder()

    static def initialize(){

        def content = sb.splitPane(orientation: JSplitPane.VERTICAL_SPLIT){
            panel(constraints: "top"){
                gridLayout(rows: 1, cols: 3)
                panel(name: "decks", border: new LineBorder(Color.green)){
                    gridLayout(rows: 1, cols: 2)
                    panel(name: "doors", border: new LineBorder(Color.darkGray)){
                        boxLayout(axis: BoxLayout.Y_AXIS)
                        label(text: 'Door Deck')
                        button(text: 'Doors', enabled: false)
                        label(text: 'Drop')
                        scrollPane{
                            table{
                                tableModel(id:'doors_drop', list: [/*TODO*/]){
                                    p ->
                                        propertyColumn(header: 'Name', propertyName: 'name')
                                        propertyColumn(header: 'Type', propertyName: 'type')
                                        propertyColumn(header: 'Info', propertyName: 'info')
                                }
                            }
                            /*GameWindowModel.instance.dropListDoors.addPropertyChangeListener({
                                e ->
                                    doors_drop.fireTableDataChanged()
                            } as PropertyChangeListener )*/
                        }
                    }
                    panel(name: "golds", border: new LineBorder(Color.yellow)){
                        boxLayout(axis: BoxLayout.Y_AXIS)
                        label(text: 'Gold Deck')
                        button(text: 'Golds', enabled: false)
                        label(text: 'Drop')
                        scrollPane{
                            table{
                                tableModel(id:'gold_drop', list: [/*TODO*/]){
                                    p ->
                                        propertyColumn(header: 'Name', propertyName: 'name')
                                        propertyColumn(header: 'Type', propertyName: 'type')
                                        propertyColumn(header: 'Info', propertyName: 'info')
                                }
                            }
                            /*GameWindowModel.instance.dropListGolds.addPropertyChangeListener({
                                e ->
                                    gold_drop.fireTableDataChanged()
                            } as PropertyChangeListener )*/
                        }
                    }
                }
                panel(name: "stack", border: new LineBorder(Color.cyan)){
                    label(text: "Game Info")
                    boxLayout(axis: BoxLayout.PAGE_AXIS)
                    scrollPane{
                        table{
                            tableModel(id:'game_info', list: GameTableModel.instance.game_info){
                                p ->
                                    propertyColumn(header: 'Parameter', propertyName: 'key');
                                    propertyColumn(header: 'Value', propertyName: 'value');
                            }
                        }
                        GameTableModel.instance.game_info.addPropertyChangeListener({
                            e ->
                                game_info.fireTableDataChanged()
                        } as PropertyChangeListener )
                    }
                    label(text: "Stack")
                    scrollPane{
                        table{
                            tableModel(id:'stack', list: GameTableModel.instance.stack){
                                p ->
                                    propertyColumn(header: 'Name', propertyName: 'name')
                                    propertyColumn(header: 'Type', propertyName: 'type')
                                    propertyColumn(header: 'Direction', propertyName: 'direction')
                            }
                        }
                        GameTableModel.instance.stack.addPropertyChangeListener({
                            e ->
                                stack.fireTableDataChanged()
                        } as PropertyChangeListener )
                    }
                }
                panel(name: "npc", border: new LineBorder(Color.black)){
                    boxLayout(axis: BoxLayout.PAGE_AXIS)
                    label(text: "Opponents")
                    scrollPane{
                        table{
                            tableModel(id:'opponents', list: GameTableModel.instance.npc){
                                p ->
                                    propertyColumn(header: 'Name', propertyName: 'name')
                                    propertyColumn(header: 'Level', propertyName: 'level')
                                    propertyColumn(header: 'Class', propertyName: 'class')
                                    propertyColumn(header: 'Race', propertyName: 'race')
                            }
                        }
                        GameTableModel.instance.npc.addPropertyChangeListener({
                            e ->
                                opponents.fireTableDataChanged()
                        } as PropertyChangeListener )
                    }
                }
            }
            panel(constraints: "bottom"){
                gridLayout(rows: 1, cols: 3)
                panel(name: "hand and equip", border: new LineBorder(Color.black)){
                    gridLayout(rows: 2, cols: 1)
                    panel(name: "hand"){
                        boxLayout(axis: BoxLayout.PAGE_AXIS)
                        label(text: "Hand")
                        scrollPane{
                            table{
                                tableModel(id:'hand', list: GameTableModel.instance.player_hand){
                                    p ->
                                        propertyColumn(header: 'Name', propertyName: 'name')
                                        propertyColumn(header: 'Type', propertyName: 'type')
                                }
                            }
                            GameTableModel.instance.player_hand.addPropertyChangeListener({
                                e ->
                                    hand.fireTableDataChanged()
                            } as PropertyChangeListener )
                        }
                    }
                    panel(name: "equip"){
                        boxLayout(axis: BoxLayout.PAGE_AXIS)
                        label(text: "Equipment")
                        scrollPane{
                            table{
                                tableModel(id:'equip', list: GameTableModel.instance.player_equip){
                                    p ->
                                        propertyColumn(header: 'Type', propertyName: 'place')
                                        propertyColumn(header: 'Name', propertyName: 'name')
                                }
                            }
                            GameTableModel.instance.player_equip.addPropertyChangeListener({
                                e ->
                                    equip.fireTableDataChanged()
                            } as PropertyChangeListener )
                        }
                    }
                }
                panel(name: "info and action", border: new LineBorder(Color.black)){
                    gridLayout(rows: 2, cols: 1)
                    panel(name: "info", border: new LineBorder(Color.blue)){
                        boxLayout(axis: BoxLayout.PAGE_AXIS)
                        label(text: "Player Info")
                        scrollPane{
                            table{
                                tableModel(id:'p_info', list: GameTableModel.instance.player_info){
                                    p ->
                                        propertyColumn(header: 'Parameter', propertyName: 'key');
                                        propertyColumn(header: 'Value', propertyName: 'value');
                                }
                            }
                            GameTableModel.instance.player_info.addPropertyChangeListener({
                                e ->
                                    p_info.fireTableDataChanged()
                            } as PropertyChangeListener )
                        }
                    }
                    panel(name: "actions", border: new LineBorder(Color.red)){
                        boxLayout(axis: BoxLayout.PAGE_AXIS)
                        label(text: "Actions")
                        scrollPane{
                            actions = table(mousePressed: {
                                popupMenu{
                                    menuItem{
                                        action(name: 'do it', closure: {
                                            GameTableModel.instance.doAction()
                                        })
                                    }
                                }.show(it.getComponent(), it.getX(), it.getY())
                            }){
                                tableModel(id:'action', list: GameTableModel.instance.actions){
                                    p ->
                                        propertyColumn(header: 'Action', propertyName: 'action');
                                }
                            }
                            GameTableModel.instance.actions.addPropertyChangeListener({
                                e ->
                                    action.fireTableDataChanged()
                            } as PropertyChangeListener )
                        }
                    }
                }
                panel(name: "game_log", border: new LineBorder(Color.black)){
                    boxLayout(axis: BoxLayout.PAGE_AXIS)
                    label(text: "Log")
                    scrollPane{
                        table{

                        }
                    }
                }
            }
        }

        MainWindowView.instance.setContent(content)
        MainWindowView.instance.setSize(800, 600)
        MainWindowView.instance.setTitle("%GAME_NAME% - Game Table")

        /*Subscribe model on events by game processor*/
        GameProcessor.instance.view = GameTableModel.instance
    }

    static JTable actions
}
