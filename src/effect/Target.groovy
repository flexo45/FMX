package effect

enum Target {
    PLAYER, MONSTER, HAND

    public static Target parseString(String value){
        switch (value.toLowerCase()){
            case "player":
                return PLAYER
            case "monster":
                return MONSTER
            case "hand":
                return HAND
            default:
                return PLAYER
        }
    }
}