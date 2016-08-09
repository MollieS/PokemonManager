package pokemonmanager.pokemon;

import pokemonmanager.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class NamedPokemon implements Pokemon {

    private String name;
    private String height;
    private List<String> abilities;

    public NamedPokemon(String name, String height, List<String> abilities) {
        this.name = name;
        this.height = height;
        this.abilities = new ArrayList<>(abilities);
    }

    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void addAbility(String ability) {
        abilities.add(ability);
    }

}
