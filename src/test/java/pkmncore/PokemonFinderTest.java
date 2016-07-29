package pkmncore;

import com.google.gson.JsonObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokemonFinderTest {

    private SearchEngine search = new SearchFake();
    private PokemonFinder pokemonFinder = new PokemonFinder(search);

    @Test
    public void findsPokemonName() throws PokemonError {
        JsonObject data = search.findByName("pikachu");
        assertEquals("pikachu", pokemonFinder.findName(data));
    }

    @Test
    public void findsPokemonHeight() throws PokemonError {
        JsonObject data = search.findByName("pikachu");
        assertEquals(4, pokemonFinder.findHeight(data));
    }

    @Test
    public void findsPokemonAbilities() throws PokemonError {
        JsonObject data = search.findByName("pikachu");
        assertEquals("lightning-rod", pokemonFinder.findAbilities(data)[0]);
        assertEquals("static", pokemonFinder.findAbilities(data)[1]);
    }

    @Test
    public void returnsAPokemon() {
        Pokemon pokemon = pokemonFinder.find("pikachu");
        assertEquals("pikachu", pokemon.getName());
        assertEquals(4, pokemon.getHeight());
        assertEquals("lightning-rod", pokemon.getAbilities()[0]);
        assertEquals("static", pokemon.getAbilities()[1]);
    }

    @Test
    public void returnsANullPokemonIfNameIsNotFound() {
        Pokemon pokemon = pokemonFinder.find("Mollie");
        assertEquals(Pokemon.NULL, pokemon);
        assertEquals("This pokemon does not exist", pokemon.getName());
        assertEquals(0, pokemon.getHeight());
        assertTrue(pokemon.getAbilities().length == 0);
    }

    @Test
    public void formatsQuery() {
        assertEquals("pikachu", pokemonFinder.formatQuery("Pikachu"));
    }
}
