package pkmncore;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SearchFake implements SearchEngine {

    public JsonObject findByName(String name) {
        String fakeJson = "{\"name\": \"pikachu\", \"height\": 4, \"abilities\":[{\"ability\":{\"name\":\"lightning-rod\"}}, {\"ability\":{\"name\":\"static\"}}]}";
        JsonObject json = new JsonParser().parse(fakeJson).getAsJsonObject();
        return json;
    }
}
