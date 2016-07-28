package pkmncore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PokemonFinder  {

    private SearchEngine searchEngine;

    public PokemonFinder(SearchEngine search) {
        this.searchEngine = search;
    }

    public Pokemon find(String name) {
        JsonObject pokemonData = searchEngine.findByName(name);
        return new Pokemon(findName(pokemonData), findHeight(pokemonData), getAbilities(pokemonData));
    }

    private String[] getAbilities(JsonObject pokemonData) {
        return new String[]{"hello"};
    }

    public int findHeight(JsonObject data) {
        return data.get("height").getAsInt();
    }

    public String findName(JsonObject data) {
        return data.get("name").getAsString();
    }

    public String[] findAbilities(JsonObject pokemonData) {
        JsonArray abilities = pokemonData.get("abilities").getAsJsonArray();
        String[] pokemonAbilities = new String[abilities.size()];
        for (int ability = 0; ability < abilities.size(); ability++) {
            JsonObject currentAbility = abilities.get(ability).getAsJsonObject();
            JsonObject abilityName = currentAbility.get("ability").getAsJsonObject();
            pokemonAbilities[ability] = abilityName.get("name").getAsString();
        }
        return pokemonAbilities;
    }
}
