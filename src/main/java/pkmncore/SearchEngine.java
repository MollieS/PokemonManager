package pkmncore;

import com.google.gson.JsonObject;

public interface SearchEngine {

    JsonObject findByName(String name);

}
