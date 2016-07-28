package pkmncore;

import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PokemonFinderTest {

    @Test
    public void findsPokemonName() {
        SearchEngine search = new SearchFake();
        PokemonFinder pokemonFinder = new PokemonFinder(search);

        JsonObject data = search.findByName("pikachu");

        assertEquals("pikachu", pokemonFinder.findName(data));
    }

    @Test
    public void findsPokemonHeight() {
        SearchEngine search = new SearchFake();
        PokemonFinder pokemonFinder = new PokemonFinder(search);

        JsonObject data = search.findByName("pikachu");

        assertEquals(4, pokemonFinder.findHeight(data));
    }

    @Test
    public void findsPokemonAbilities() {
        SearchEngine search = new SearchFake();
        PokemonFinder pokemonFinder = new PokemonFinder(search);

        JsonObject data = search.findByName("pikachu");

        assertEquals("lightning-rod", pokemonFinder.findAbilities(data)[0]);
        assertEquals("static", pokemonFinder.findAbilities(data)[1]);
    }

    @Test
    public void returnsAPokemon() {
        PokemonFinder pokemonFinder = new PokemonFinder(new SearchFake());
        Pokemon pokemon = pokemonFinder.find("pikachu");

        assertEquals("pikachu", pokemon.getName());
        assertEquals(4, pokemon.getHeight());
        assertEquals("lightning-rod", pokemon.getAbilities()[0]);
        assertEquals("static", pokemon.getAbilities()[1]);
    }
}
