package ui.gametable

import cards.Item
import cards.Profession
import cards.Race
import equipment.EquipmentItem
import equipment.EquipmentManager
import gamemanager.ActionProcessor
import gamemanager.GameProcessor
import gamemanager.Player
import interfaces.ICard
import groovy.beans.Bindable

class GameTableModel implements GameTableEvents {

    private static GameTableModel instance
    public static GameTableModel getInstance(){
        if(instance == null) instance = new GameTableModel()
        return instance
    }

    private GameTableModel() { }

    public void doAction(){
        def action = (String)GameTableView.actions.getValueAt(GameTableView.actions.selectedRow, 0)
        ActionProcessor.processActions(action)
    }

    @Bindable
    volatile ObservableList player_hand = []

    @Bindable
    volatile ObservableList player_equip = []

    @Bindable
    volatile ObservableList game_info = [[key:'Turn ->', value:'1']
                                , [key:'Round ->', value:'1']
                                , [key:'Turn of player ->', value:GameProcessor.instance.currentPlayer.name]]

    @Bindable
    volatile ObservableList player_info = []

    @Bindable
    volatile ObservableList actions = []

    @Bindable
    volatile ObservableList npc = []

    @Bindable
    volatile ObservableList stack = []

    @Override
    synchronized void playerHandChangedNotify() {
        player_hand.clear()
        Player player = GameProcessor.instance.game.playerList.find{ !it.npc }
        player.hand.each {
            player_hand.add([name:it.name, type:it.getClass().name])
        }
    }

    @Override
    synchronized void gameInfoChangedNotify() {
        game_info.clear()
        game_info.add([key: 'Turn ->', value: GameProcessor.instance.game.turn])
        game_info.add([key: 'Round ->', value: GameProcessor.instance.round])
        game_info.add([key: 'Turn of player ->', value: GameProcessor.instance.currentPlayer.name])
    }

    @Override
    synchronized void actionChangedNotify(int situation) {
        actions.clear()
        ActionListManager.getActions(situation).each {
            actions.add([action:it])
        }
    }

    @Override
    synchronized void playersChangedNotify() {
        npc.clear()
        player_info.clear()
        GameProcessor.instance.game.playerList.each {
            if(it.npc){
                npc.add([name:it.name, level:it.level, class:((Profession)it.c1ass).name, race:((Race)it.race).name])
            }
            else if(!it.npc) {
                player_info.add([key: 'Name', value: it.name])
                player_info.add([key: 'Sex', value: it.sex ? 'male' : 'female'])
                player_info.add([key: 'Level', value: it.level])
                player_info.add([key: 'Class', value: ((Profession)it.c1ass).name])
                player_info.add([key: 'Race', value: ((Race)it.race).name])
                player_info.add([key: 'Rating', value: it.getBaseRating()])
            }
        }
    }

    @Override
    synchronized void cardAddedInStack(ICard card, String direction) {
        stack.add(name: card.name, type: card.class.name, direction: direction)
    }

    @Override
    synchronized void equipmentChangedNotify() {
        player_equip.clear()
        GameProcessor.instance.getPlayer().equipment.equipment.each {
            EquipmentItem eIt = (EquipmentItem)it.get('eq_item');
            Item item = (Item)it.get('item')

            player_equip.add([place: EquipmentManager.getPlace(eIt.itemType), name: item.name])
        }
    }

    @Override
    synchronized void stackCleared() {
        stack.clear()
    }
}
