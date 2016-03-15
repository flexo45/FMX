package gamedata.dao

import log.Log

class GameDataDao {

    private static Log logger = new Log(GameDataDao.class.name)

    private boolean configIsOutofdate

    public boolean isConfigOutdate(){
        return configIsOutofdate
    }

    public void setGameData(String path, String content){

        FileWriter fileWriter = new FileWriter(path)

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)

        bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")

        bufferedWriter.newLine()

        bufferedWriter.write(content)

        bufferedWriter.close()

        configIsOutofdate = true
    }

    public Node getGameData(String path){
        FileReader fileReader = null
        Node config = null

        try{
            fileReader = new FileReader(path)
        }
        catch (FileNotFoundException e){

            logger.error("Config not found $path, default was used", e)

            setGameData(path, "<data><settings><card-set path=\"base_set.xml\" /></settings></data>")

            fileReader = new FileReader(path)
        }
        catch (IOException e){
            logger.error("Open default config failed", e)
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

            config = parser.parseText(c_text)
        }
        catch (Exception e){
            logger.error("Read config failed", e)
        }

        configIsOutofdate = false

        return config
    }
}
