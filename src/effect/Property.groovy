package effect

enum Property {
    LEVEL, PROFESSION, RATING

    public static Property parseString(String value){
        switch (value.toLowerCase()){
            case "level":
                return LEVEL
            case "profession":
                return PROFESSION
            case "rating":
                return RATING
            default:
                return null
        }
    }
}