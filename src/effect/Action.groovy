package effect

public enum Action {
    CHANGE, LOSE, VARY

    public static Action parseString(String value){
        switch (value.toLowerCase()){
            case "lose":
                return LOSE
            case "vary":
                return VARY
            case "change":
                return CHANGE
            default:
                return null
        }
    }
}