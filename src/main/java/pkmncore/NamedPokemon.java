package pkmncore;

public class NamedPokemon implements Pokemon {

    private String name;
    private int height;
    private String[] abilities;

    public NamedPokemon(String name, int height, String[] abilities) {
        this.name = name;
        this.height = height;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public String[] getAbilities() {
        return abilities;
    }
}
