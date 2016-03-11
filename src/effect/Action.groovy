package effect

public enum Action {
    INCREASE, DECREASE, CHANGE

    public static Action parseString(String value){
        switch (value.toLowerCase()){
            case "increase":
                return INCREASE
            case "decrease":
                return DECREASE
            case "change":
                return CHANGE
            default:
                return null
        }
    }
}