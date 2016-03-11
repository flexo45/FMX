package gamemanager

import cards.Curse
import cards.Item
import cards.Monster
import cards.Profession
import cards.Race
import cards.Spell
import equipment.EquipmentManager
import fightmanager.Fight
import interfaces.ICard
import log.Log
import ui.gametable.ActionListManager
import ui.gametable.InfoPopup
import ui.gametable.SelectCardPopup

class PlayerActions {
    static void equipItem(){
        SelectCardPopup.initialize(Item.class)

        if(GameProcessor.instance.cardForUsing != null){
            Item item = ((Item)GameProcessor.instance.cardForUsing)
            if(EquipmentManager.isEquipment(item)){
                GameProcessor.instance.getPlayer().equipment.addItem(item)
                GameProcessor.instance.getPlayer().hand.remove(item)
            }
        }

        GameProcessor.instance.view.gameInfoChangedNotify()
        GameProcessor.instance.view.playerHandChangedNotify()
        GameProcessor.instance.view.equipmentChangedNotify()

        Log.print(this, "Item was used: ${GameProcessor.instance.cardForUsing.name}")
    }

    static void fightWithMonsterFromHand(){
        SelectCardPopup.initialize(Monster.class)

        if(GameProcessor.instance.cardForUsing != null){
            GameProcessor.instance.fighting = new Fight(monster: ((Monster)GameProcessor.instance.cardForUsing)
                    , hero: GameProcessor.instance.getPlayer())

            GameProcessor.instance.view.cardAddedInStack(GameProcessor.instance.cardForUsing
                    , "${GameProcessor.instance.player.name} -> self")

            GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT)
        }

        Log.print(this, "Player fight with monster: ${GameProcessor.instance.cardForUsing.name}")
    }
    static void castSpell(){
        SelectCardPopup.initialize(Spell.class)
        if(GameProcessor.instance.cardForUsing != null){
            ((Spell)GameProcessor.instance.cardForUsing).cast()
        }

        Log.print(this, "Spell was used: ${GameProcessor.instance.cardForUsing.name}")
    }
    static void useItem(){
        SelectCardPopup.initialize(Item.class)
        if(GameProcessor.instance.cardForUsing != null){
            ((Item)GameProcessor.instance.cardForUsing).use()
        }

        Log.print(this, "Item was used: ${GameProcessor.instance.cardForUsing.name}")
    }
    static void getCardInclosed(){
        ICard card = GameProcessor.instance.game.doors.getNextCard()

        InfoPopup.initialize("You get the card '${card.name}'\r\n\r\nDescription:\r\n${card.info}")

        GameProcessor.instance.getPlayer().hand.add(card)

        GameProcessor.instance.round = 3

        GameProcessor.instance.view.playerHandChangedNotify()

        GameProcessor.instance.view.actionChangedNotify(ActionListManager.SECOND_ROUND_END)

        Log.print(this, "Player get card '${card.name}' inclosed!")
    }
    static void selectRace(){
        SelectCardPopup.initialize(Race.class)

        ICard oldRace = (ICard)GameProcessor.instance.getPlayer().race

        if(GameProcessor.instance.cardForUsing != null){
            GameProcessor.instance.getPlayer().race = ((Race)GameProcessor.instance.cardForUsing)
        }

        GameProcessor.instance.view.playersChangedNotify()

        GameProcessor.instance.view.playerHandChangedNotify()

        Log.print(this, "Player change race, old: ${oldRace.name}, new: ${((ICard)GameProcessor.instance.getPlayer().race).name}")
    }
    static void selectClass(){
        SelectCardPopup.initialize(Profession.class)

        ICard oldClass = (ICard)GameProcessor.instance.getPlayer().c1ass

        if(GameProcessor.instance.cardForUsing != null){
            GameProcessor.instance.getPlayer().c1ass = ((Profession)GameProcessor.instance.cardForUsing)
        }

        GameProcessor.instance.view.playersChangedNotify()

        GameProcessor.instance.view.playerHandChangedNotify()

        GameProcessor.instance.view.stackCleared()

        Log.print(this, "Player change class, old: ${oldClass.name}, new: ${((ICard)GameProcessor.instance.getPlayer().c1ass).name}")
    }
    static void getCardFromStack(){
        GameProcessor.instance.getPlayer().hand.add(CardStackBuffer.cardList.get(0))

        GameProcessor.instance.round = 2

        GameProcessor.instance.view.playerHandChangedNotify()

        GameProcessor.instance.view.stackCleared()

        GameProcessor.instance.view.actionChangedNotify(ActionListManager.SECOND_ROUND_BEGIN)

        Log.print(this, "Player get card to hand!")
    }
    static void openDoor(){
        ICard card = GameProcessor.instance.game.doors.getNextCard()

        CardStackBuffer.cardList.add(card)

        GameProcessor.instance.view.cardAddedInStack(card, "deck -> ${GameProcessor.instance.getPlayer()}")

        if(card.class.equals(Monster.class)){

            GameProcessor.instance.fighting = new Fight(monster: (Monster)card
                    , hero: GameProcessor.instance.getPlayer())

            GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT)
        }
        else if(card.class.equals(Curse.class)){
            GameProcessor.instance.view.actionChangedNotify(ActionListManager.CURSE)
        }
        else {
            GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIRST_ROUND_END)
        }

        Log.print(this, "Door opend! It's a ${card.class.getName()}, ${card.name}")
    }
}
