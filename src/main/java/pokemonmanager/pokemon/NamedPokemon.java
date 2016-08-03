package pokemonmanager.pokemon;

import pokemonmanager.Pokemon;

public class NamedPokemon implements Pokemon {

    private String name;
    private String height;
    private String[] abilities;

    public NamedPokemon(String name, String height, String[] abilities) {
        this.name = name;
        this.height = height;
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
