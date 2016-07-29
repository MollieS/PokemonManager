package pkmncore;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokemonFinderTest {

    private PokemonFinder pokemonFinder = new PokemonFinder(new SearchFake());

    @Test
    public void findsPokemonName() {
        Pokemon pokemon = pokemonFinder.find("pikachu");
        assertEquals("pikachu", pokemon.getName());
    }

    @Test
    public void findsPokemonHeight() {
        Pokemon pokemon = pokemonFinder.find("pikachu");
        assertEquals(4, pokemon.getHeight());
    }

    @Test
    public void findsPokemonAbilities() {
        Pokemon pokemon = pokemonFinder.find("pikachu");
        String[] abilities = pokemon.getAbilities();
        assertEquals("lightning-rod", abilities[0]);
        assertEquals("static", abilities[1]);
        assertTrue(abilities.length == 2);
    }

    @Test
    public void formatsQuery() {
        assertEquals("pikachu", pokemonFinder.formatQuery("Pikachu"));
    }
}
