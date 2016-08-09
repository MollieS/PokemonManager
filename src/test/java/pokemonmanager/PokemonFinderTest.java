package pokemonmanager;

import org.junit.Test;
import pokemonmanager.search.PokemonFinder;
import pokemonmanager.testfakes.SearchFake;

import java.util.List;

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

        List<String> abilities = pokemon.getAbilities();

        assertTrue(abilities.size() == 2);
        assertEquals("lightning-rod", abilities.get(0));
        assertEquals("static", abilities.get(1));
    }

    @Test
    public void returnsANullPokemonIfNameIsNotFound() {
        Pokemon pokemon = pokemonFinder.find("Mollie");

        assertTrue(pokemon.getAbilities().size() == 0);
        assertEquals(Pokemon.NULL, pokemon);
        assertEquals("This pokemon does not exist", pokemon.getName());
        assertEquals("", pokemon.getHeight());
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
