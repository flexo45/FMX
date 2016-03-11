package effect

class Effect {
    int id
    Target target
    Property property
    Action action

    @Override
    public String toString(){
        return "$action the $property on $target"
    }
}
