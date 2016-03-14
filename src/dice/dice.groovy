package dice

class Dice {

    private static Random rand = new Random()

    public static int rollD6(){
        return rand.nextInt(5) + 1
    }

}
