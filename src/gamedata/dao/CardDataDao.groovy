package gamedata.dao

import log.Log

class CardDataDao {

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
            Log.print(this, "ERROR: open card set failed: ${e.toString()}")
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
            Log.print(this, "ERROR: card set read failed: ${e.toString()}")
        }

        cardsetIsOutofdate = false

        Log.print(this, "DEBUG: card set successful readed")

        return cards_set
    }
}
