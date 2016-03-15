package gamedata.dao

import gamedata.GameDataManager
import log.Log

class SettingsDao {

    private static Log logger = new Log(SettingsDao.class.name)

    public String getCardSetPath(){

        Node config = GameDataManager.instance.getConfig()

        try {
            return ((config.find{ it.name().equals('settings') } as Node)
                    .find{ it.name().equals('card-set') } as Node)
                    .attribute('path')
                    .toString()
        }
        catch (NullPointerException e){
            logger.error("card-set path not found in settings", e)
            return null
        }
    }

}
