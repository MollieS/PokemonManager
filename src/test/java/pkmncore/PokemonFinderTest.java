package pkmncore;

import org.junit.Test;
import pkmncore.search.PokemonFinder;
import pkmncore.testfakes.SearchFake;

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
        assertEquals("4", pokemon.getHeight());
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
    public void returnsANullPokemonIfNameIsNotFound() {
        Pokemon pokemon = pokemonFinder.find("Mollie");
        assertEquals(Pokemon.NULL, pokemon);
        assertEquals("This pokemon does not exist", pokemon.getName());
        assertEquals(new String(), pokemon.getHeight());
        assertTrue(pokemon.getAbilities().length == 0);
    }

    @Test
    public void changesQueryToLowerCase() {
        assertEquals("pikachu", pokemonFinder.formatQuery("Pikachu"));
    }

    @Test
    public void stripsWhiteSpace() {
        assertEquals("bulbasaur", pokemonFinder.formatQuery("bulbasaur "));
    }
}
