package equipment


class NoFreeCellException extends Exception{
    public NoFreeCellException(String message){
        super(message)
    }

    public NoFreeCellException(String message, Throwable couse){
        super(message, couse)
    }

}
