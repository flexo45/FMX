package effect

enum Property {
    LEVEL, PROFESSION, RATING, RACE, ITEM, HAND

    public static Property parseString(String value){
        switch (value.toLowerCase()){
            case "level":
                return LEVEL
            case "profession":
                return PROFESSION
            case "rating":
                return RATING
            case "race":
                return RACE
            case "item":
                return ITEM
            case "hand":
                return HAND
            default:
                return null
        }
    }
}