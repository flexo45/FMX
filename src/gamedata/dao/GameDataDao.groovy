package gamedata.dao

import log.Log

class GameDataDao {

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

        Log.print(this, "DEBUG: config $path successful updated")
    }

    public Node getGameData(String path){
        FileReader fileReader = null
        Node config = null

        try{
            fileReader = new FileReader(path)
        }
        catch (FileNotFoundException e){

            Log.print(this, "WARNING: Config not found: $e")

            setGameData(path, "<data><settings><card-set path=\"base_set.xml\" /></settings></data>")

            fileReader = new FileReader(path)
        }
        catch (IOException e){
            Log.print(this, "ERROR: open config failed: $e")
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
            Log.print(this, "ERROR: read config failed: $e")
        }

        configIsOutofdate = false

        Log.print(this, "DEBUG: config '$path' successful readed")

        return config
    }
}
