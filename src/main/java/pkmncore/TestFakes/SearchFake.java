package pkmncore.testfakes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import pkmncore.PokemonError;
import pkmncore.SearchEngine;

public class SearchFake implements SearchEngine {

    public JsonObject findByName(String name) throws PokemonError {
        if ("pikachu".equals(name)) {
            String fakeJson = "{\"name\": \"pikachu\", \"height\": 4, \"abilities\":[{\"ability\":{\"name\":\"lightning-rod\"}}, {\"ability\":{\"name\":\"static\"}}]}";
            return new JsonParser().parse(fakeJson).getAsJsonObject();
        } else {
            throw new PokemonError("That pokemon does not exist");
        }
    }
}
