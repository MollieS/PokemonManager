package pkmncore;

import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchTest {

    @Test
    public void getsAFakeJSONResponse() {
        SearchFake fake = new SearchFake();
        JsonObject response = fake.findByName("pikachu");
        assertEquals("pikachu", response.get("name").getAsString());
    }
}
