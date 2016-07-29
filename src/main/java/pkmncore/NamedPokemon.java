package pkmncore;

public class NamedPokemon implements Pokemon {

    private String name;
    private String height;
    private String[] abilities;

    public NamedPokemon(String name, int height, String[] abilities) {
        this.name = name;
        this.height = String.valueOf(height);
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public String[] getAbilities() {
        return abilities;
    }
}
