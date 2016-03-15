package gamemanager

import cards.Curse
import cards.Item
import cards.Monster
import cards.Profession
import cards.Race
import cards.Spell
import effect.Target
import equipment.NoFreeCellException
import equipment.NotEquipmenItemException
import fightmanager.Fight
import interfaces.ICard
import log.Log
import ui.gametable.ActionListManager
import ui.gametable.InfoPopup
import ui.gametable.SelectCardPopup
import ui.gametable.SelectTargetPopup

class PlayerActions {

    private static Log logger = new Log(PlayerActions.class.name)

    static void equipItem(){
        SelectCardPopup.initialize(Item.class)

        ICard card = GameProcessor.instance.cardForUsing

        if(card != null){
            Item item = ((Item)GameProcessor.instance.cardForUsing)
            try{
                GameProcessor.instance.getPlayer().equipment.addItem(item)
                GameProcessor.instance.getPlayer().hand.remove(item)
                logger.info("Item was used: $item")
            }
            catch (NoFreeCellException e){
                e.toString()
                logger.info("No free cell for item: $item")
            }
            catch (NotEquipmenItemException e){
                e.toString()
                logger.info("Item $item can't equiped")
            }
        }

        GameProcessor.instance.view.gameInfoChangedNotify()
        GameProcessor.instance.view.playerHandChangedNotify()
        GameProcessor.instance.view.equipmentChangedNotify()
        GameProcessor.instance.view.playersChangedNotify()
    }

    static void fightWithMonsterFromHand(){
        SelectCardPopup.initialize(Monster.class)

        ICard card = GameProcessor.instance.cardForUsing

        if(card != null){
            GameProcessor.instance.fighting = new Fight(monster: ((Monster)card)
                    , hero: GameProcessor.instance.getPlayer())

            GameProcessor.instance.view.cardAddedInStack(card
                    , "${GameProcessor.instance.player.name} -> self")

            GameProcessor.instance.view.actionChangedNotify(ActionListManager.FIGHT)
        }

        logger.info("Player fight with monster: $card}")
    }
    static void castSpell(){
        SelectCardPopup.initialize(Spell.class)

        ICard spell_card = GameProcessor.instance.cardForUsing

        if(spell_card != null){
            SelectTargetPopup.initialize(spell_card.effect.target)

            Object target = GameProcessor.instance.targetForUsing

            if(target != null){
                if(spell_card.effect.target == Target.MONSTER &&
                GameProcessor.instance.fighting == null){
                    logger.info("Can cast in battle only: $spell_card, ignore")
                }
                else {
                    ((Spell)spell_card).cast(target)

                    logger.info("Spell $spell_card was used on $target")
                }
            }
        }
    }
    static void useItem(){
        SelectCardPopup.initialize(Item.class)

        ICard item_card = GameProcessor.instance.cardForUsing

        if(item_card != null){

            SelectTargetPopup.initialize(item_card.effect.target)

            Object target = GameProcessor.instance.targetForUsing

            if(target != null){
                if(item_card.effect.target == Target.MONSTER &&
                        GameProcessor.instance.fighting == null){
                    logger.info("Can use in battle only: $item_card, ignore")
                }
                else {
                    ((Item)item_card).useIt(target)

                    logger.info("Item $item_card was used on $target")
                }
            }
        }
    }
    static void getCardInclosed(){
        ICard card = GameProcessor.instance.game.doors.getNextCard()

        logger.info("Player get the card '$card', description: ${card.info}")

        GameProcessor.instance.getPlayer().hand.add(card)

        GameProcessor.instance.round = 3

        GameProcessor.instance.view.playerHandChangedNotify()

        GameProcessor.instance.view.actionChangedNotify(ActionListManager.SECOND_ROUND_END)

        logger.info("Player get card '$card' inclosed!")
    }
    static void selectRace(){
        SelectCardPopup.initialize(Race.class)

        ICard oldRace = (ICard)GameProcessor.instance.getPlayer().race

        ICard newRace = GameProcessor.instance.cardForUsing

        if(newRace != null){
            GameProcessor.instance.getPlayer().race = ((Race)newRace)
            logger.info("Player change race, old: ${oldRace}, new: ${((ICard)GameProcessor.instance.getPlayer().race)}")
        }

        GameProcessor.instance.view.playersChangedNotify()
        GameProcessor.instance.view.playerHandChangedNotify()
    }
    static void selectClass(){
        SelectCardPopup.initialize(Profession.class)

        ICard oldClass = (ICard)GameProcessor.instance.getPlayer().c1ass

        ICard newClass = GameProcessor.instance.cardForUsing

        if(newClass != null){
            GameProcessor.instance.getPlayer().c1ass = ((Profession)newClass)
            logger.info("Player change class, old: ${oldClass}, new: ${((ICard)GameProcessor.instance.getPlayer().c1ass)}")
        }

        GameProcessor.instance.view.playersChangedNotify()
        GameProcessor.instance.view.playerHandChangedNotify()
        GameProcessor.instance.view.stackCleared()
    }
    static void getCardFromStack(){
        GameProcessor.instance.getPlayer().hand.add(CardStackBuffer.cardList.get(0))

        GameProcessor.instance.round = 2

        GameProcessor.instance.view.playerHandChangedNotify()

        GameProcessor.instance.view.stackCleared()

        GameProcessor.instance.view.actionChangedNotify(ActionListManager.SECOND_ROUND_BEGIN)

        logger.info("Player get card to hand!")
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

        logger.info("Door opend! It's a ${card}")
    }
}
