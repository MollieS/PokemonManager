package pkmncore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PokemonFinder {

    private SearchEngine searchEngine;
    private boolean isFound;

    public PokemonFinder(SearchEngine search) {
        this.searchEngine = search;
    }

    public Pokemon find(String name) {
        JsonObject pokemonData = getPokemonData(name);
        return returnPokemon(pokemonData);
    }

    private Pokemon returnPokemon(JsonObject pokemonData) {
        if (isFound) {
            return new NamedPokemon(findName(pokemonData), findHeight(pokemonData), findAbilities(pokemonData));
        } else {
            return Pokemon.NULL;
        }
    }

    private JsonObject getPokemonData(String name) {
        JsonObject pokemonData = null;
        try {
            this.isFound = true;
            pokemonData = searchEngine.findByName(name);
        } catch (PokemonError pokemonError) {
            this.isFound = false;
        }
        return pokemonData;
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
        return query.toLowerCase().trim();
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
