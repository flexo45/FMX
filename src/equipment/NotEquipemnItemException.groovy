package equipment

class NotEquipmenItemException extends Exception{
    public NotEquipmenItemException(String message){super(message)}
    public NotEquipmenItemException(String message, Throwable cause){super(message, cause)}
}
