package pkmncore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PokemonFinder {

    private SearchEngine searchEngine;

    public PokemonFinder(SearchEngine search) {
        this.searchEngine = search;
    }

    public Pokemon find(String name) {
        JsonObject pokemonData = searchEngine.findByName(name);
        return new Pokemon(findName(pokemonData), findHeight(pokemonData), findAbilities(pokemonData));
    }

    public int findHeight(JsonObject data) {
        return data.get("height").getAsInt();
    }

    public String findName(JsonObject data) {
        return data.get("name").getAsString();
    }

    public String[] findAbilities(JsonObject pokemonData) {
        JsonArray abilities = pokemonData.get("abilities").getAsJsonArray();
        return getAbilities(abilities);
    }

    public String formatQuery(String query) {
        return query.toLowerCase();
    }

    private String[] getAbilities(JsonArray abilities) {
        String[] pokemonAbilities = new String[abilities.size()];
        for (int ability = 0; ability < abilities.size(); ability++) {
            pokemonAbilities[ability] = getAbilityName(abilities, ability);
        }
        return pokemonAbilities;
    }

    private String getAbilityName(JsonArray abilities, int ability) {
        JsonObject currentAbility = abilities.get(ability).getAsJsonObject().get("ability").getAsJsonObject();
        return currentAbility.get("name").getAsString();
    }
}
