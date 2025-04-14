package domain;

public enum TipoAlimentacion {
    CARNIVORO,
    HERBIVORO;
    

    public boolean esCarnivoro () {
        return this == CARNIVORO;
    }

    public boolean esHerbivoro(){
        return this == HERBIVORO;
    }
}
