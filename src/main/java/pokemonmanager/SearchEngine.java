package pokemonmanager;

import com.google.gson.JsonObject;

public interface SearchEngine {

    JsonObject findByName(String name) throws PokemonError;

}
