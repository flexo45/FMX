package gamemanager

import dao.CardsManagerDao
import interfaces.ICard
import log.Log

class CardManager {
    private static CardManager instance
    public static CardManager getInstance(){
        if(instance == null) instance = new CardManager()
        return instance
    }
    private CardManager(){
        doorSet = CardsManagerDao.getDoorsSet()
        goldSet = CardsManagerDao.getGoldsSet()

        Log.print(this, "initialized: doorSet:${doorSet}")
        Log.print(this, "initialized: goldSet:${goldSet}")
    }

    List<Map> doorSet
    List<Map> goldSet

    ICard getCardById(Integer id){
        def card
        doorSet.each {
            if(Integer.parseInt((String)it.get('id')) == id) {
                card = (ICard)it.get('card')
            }
        }
        goldSet.each {
            if(Integer.parseInt((String)it.get('id')) == id) {
                card = (ICard)it.get('card')
            }
        }

        Log.print(this, "card $card was found by id $id")

        return card
    }

    String getIdOfCard(ICard card){
        def id
        doorSet.each {
            if((ICard)it.get('card') == card) {
                id = (String)it.get('id')
            }
        }
        goldSet.each {
            if((ICard)it.get('card') == card) {
                id = (String)it.get('id')
            }
        }

        Log.print(this, "id $id was found by card $card")

        return id
    }
}
