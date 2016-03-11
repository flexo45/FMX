package effect

enum Target {
    PLAYER

    public static Target parseString(String value){
        switch (value.toLowerCase()){
            case "player":
                return PLAYER
            default:
                return PLAYER
        }
    }
}