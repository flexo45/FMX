package dao

import decks.DoorsDeck
import decks.GoldsDeck
import gamemanager.CardManager
import gamemanager.Game
import gamemanager.Player
import interfaces.ICard
import interfaces.IClass
import interfaces.IRace
import log.Log

class GameManagerDao {

    final static String CONFIG_FILE_NAME = "game_data.xml"

    private static Node config

    public static List<String> getGameList(){
        readConfig()

        def list = []

        config.children().each {
            Node n ->
                if(n.name().equals('game')){ list.add(n.attribute('id')) }
        }

        return list
    }

    public static Game loadGame(String gameId){

        readConfig()

        Node n_game = (Node)config.find{ it.name().equals('game') && it.attribute('id').equals(gameId) }

        def p_list = []
        def turn
        def doors = []
        def doors_drop = []
        def golds = []
        def golds_drop = []
        def def_race = null
        def def_class = null

        turn = Integer.parseInt((String)n_game.attribute('turn'))
        CardManager.instance.doorSet.each {
            c ->
                if((String)c.get('id') == '10000'){
                    def_race = c.get('card')
                }
                else if((String)c.get('id') == '20000'){
                    def_class = c.get('card')
                }
        }

        n_game.children().each {
            Node n ->
                if(n.name().equals('players')){
                    n.children().each {
                        Node p ->
                            def o_p = new Player(name: p.attribute('name')
                                    , sex: p.attribute('sex').equals("1")
                                    , level: Integer.parseInt((String)p.attribute('level'))
                                    , c1ass: (IClass)CardManager.instance.getCardById(Integer.parseInt((String)p.attribute('class')))
                                    , race: (IRace)CardManager.instance.getCardById(Integer.parseInt((String)p.attribute('race')))
                                    , npc: p.attribute('npc').equals('1'))

                            String s = ((Node)p.find{ it.name().equals('hand') }).value()?:"".toString()
                            s.minus('[').minus(']').split(";").each {
                                if(!it.isEmpty()){o_p.hand.add(CardManager.instance.getCardById(Integer.parseInt(it)))}
                            }
                        p_list.add(o_p)
                    }
                }
                else if(n.name().equals('doors')){
                    String s = n.value()?:"".toString()
                    s.minus('[').minus(']').split(";").each {
                        if(!it.isEmpty()){doors.add(CardManager.instance.getCardById(Integer.parseInt(it)))}
                    }
                }
                else if(n.name().equals('doors_drop')){
                    String s = n.value()?:"".toString()
                    s.minus('[').minus(']').split(";").each {
                        if(!it.isEmpty()){doors_drop.add(CardManager.instance.getCardById(Integer.parseInt(it)))}
                    }
                }
                else if(n.name().equals('golds')){
                    String s = n.value()?:"".toString()
                    s.minus('[').minus(']').split(";").each {
                        if(!it.isEmpty()){golds.add(CardManager.instance.getCardById(Integer.parseInt(it)))}
                    }
                }
                else if(n.name().equals('golds_drop')){
                    String s = n.value()?:"".toString()
                    s.minus('[').minus(']').split(";").each {
                        if(!it.isEmpty()){golds_drop.add(CardManager.instance.getCardById(Integer.parseInt(it)))}
                    }
                }
        }

        Game game = new Game(gameName: gameId
                , playerList: p_list
                , turn: turn
                , doors: new DoorsDeck(doors, doors_drop)
                , golds: new GoldsDeck(golds, golds_drop)
                , defaultRace: (IRace)def_race
                , defaultClass: (IClass)def_class)

        Log.print(this, "load, players: $p_list")
        Log.print(this, "load, doors: $doors")
        Log.print(this, "load, golds: $golds")
        Log.print(this, "load, def class: $def_class")
        Log.print(this, "load, def race: $def_race")
        Log.print(this, "game $gameId loaded success")

        return game
    }

    public static String getCardSetPath(){
        return ((Node)((Node)config
                .find{ it.name().equals('settings') })
                .find{ it.name().equals('card-set') })
                .attribute('path').toString()
    }

    public static void saveGame(Game game){

        readConfig()

        Node n_game = (Node)config.find{ it.name().equals('game') && it.attribute('id').equals(game.gameName) }
        if(n_game == null){
            n_game = config.appendNode('game', [id:game.gameName, turn:game.turn])
        }
        else {
            n_game.attributes().put('turn', game.turn)
            n_game.children().clear()
        }
        Node players = n_game.appendNode('players')
        game.playerList.each {
            Node player = players.appendNode('player', [name:it.name, sex:it.sex ? '1' : '0', level:it.level.toString()
                                      , class:CardManager.instance.getIdOfCard((ICard)it.c1ass)
                                      , race:CardManager.instance.getIdOfCard((ICard)it.race)
                                      , npc: it.npc ? '1' : '0'])

            def v_hand = new StringBuilder()
            it.hand.each {
                c ->
                v_hand.append(CardManager.instance.getIdOfCard(c) + ';')
            }

            player.appendNode('hand').setValue(v_hand)
        }

        def v_cards = new StringBuilder()
        game.doors.deck.each {
            v_cards.append(CardManager.instance.getIdOfCard(it) + ';')
        }
        n_game.appendNode('doors').setValue(v_cards)

        v_cards = new StringBuilder()
        game.doors.drop.each {
            v_cards.append(CardManager.instance.getIdOfCard(it) + ';')
        }
        n_game.appendNode('doors_drop').setValue(v_cards)

        StringWriter writer = new StringWriter()
        XmlNodePrinter printer = new XmlNodePrinter(new PrintWriter(writer))
        printer.preserveWhitespace = true
        printer.print(config)
        updateConfig(writer.toString())

        Log.print(this, "game saved success")
    }

    public static void readConfig() throws Exception{

        FileReader fileReader

        try{
            fileReader = new FileReader(CONFIG_FILE_NAME)
        }
        catch (FileNotFoundException e){

            Log.print(this, "WARNING: Config not found")

            updateConfig("<data><settings><card-set path=\"base_set.xml\" /></settings></data>")

            fileReader = new FileReader(CONFIG_FILE_NAME)
        }
        catch (IOException e){
            throw new Exception("OPEN_CONFIG_ERROR: ${e.toString()}")
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
            throw new Exception("READ_CONFIG_ERROR: ${e.toString()}")
        }

        Log.print(this, "config read success")
    }

    private static void updateConfig(String content){

        FileWriter fileWriter = new FileWriter(CONFIG_FILE_NAME)
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)

        bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
        bufferedWriter.newLine()
        bufferedWriter.write(content)

        bufferedWriter.close()

        Log.print(this, "config updated success")
    }
}
