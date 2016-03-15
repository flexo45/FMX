package gamedata.dao

import log.Log

class CardDataDao {

    Log logger = new Log(CardDataDao.class.name)

    private boolean cardsetIsOutofdate

    public boolean isCasdSetOutdate(){
        return cardsetIsOutofdate
    }

    public Node getCardSet(String path){
        FileReader fileReader = null
        Node cards_set = null

        try{
            fileReader = new FileReader(path)
        }
        catch (Exception e){
            logger.error("Open card set failed", e)
        }

        try{
            BufferedReader bufferedReader = new BufferedReader(fileReader)

            String c_text = ""
            String line = ""

            while ((line = bufferedReader.readLine()) != null){
                c_text += line
            }

            bufferedReader.close()

            XmlParser parser = new XmlParser()
            cards_set = parser.parseText(c_text)
        }
        catch (Exception e){
            logger.error("Card set read failed", e)
        }

        cardsetIsOutofdate = false

        return cards_set
    }
}
