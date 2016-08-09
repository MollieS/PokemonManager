package pokemonmanager;

import org.junit.Test;
import pokemonmanager.pokemon.NamedPokemon;
import pokemonmanager.storage.PokemonManager;
import pokemonmanager.testfakes.StorageFake;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokemonManagerTest {

    private PokemonManager pokemonManager = new PokemonManager(new StorageFake());

    @Test
    public void canViewCaughtPokemon() throws PokemonError {
        Pokemon pokemon = new NamedPokemon("charmander", "8", Arrays.asList("flame-body", "lava"));
        pokemonManager.catchPokemon(pokemon);
        List<Pokemon> caughtPokemon = pokemonManager.viewCaughtPokemon();
        assertEquals("charmander", caughtPokemon.get(0).getName());
        assertEquals("8", caughtPokemon.get(0).getHeight());
        assertEquals("flame-body", caughtPokemon.get(0).getAbilities().get(0));
        assertEquals("lava", caughtPokemon.get(0).getAbilities().get(1));
    }

    @Test
    public void canAddManyPokemon() throws PokemonError {
        Pokemon pokemon = new NamedPokemon("pikachu", "4", Arrays.asList("static", "lightning-rod"));
        Pokemon pokemon1 = new NamedPokemon("squirtle", "8", Arrays.asList("water-body", "whirlpool"));
        pokemonManager.catchPokemon(pokemon);
        pokemonManager.catchPokemon(pokemon1);
        List<Pokemon> caughtPokemon = pokemonManager.viewCaughtPokemon();
        assertEquals("pikachu", caughtPokemon.get(0).getName());
        assertEquals("squirtle", caughtPokemon.get(1).getName());
        assertTrue(caughtPokemon.size() == 2);
    }

    @Test
    public void ifNoPokemonAreCaughtItShowsAnEmptyList() throws PokemonError {
        List<Pokemon> pokemon = pokemonManager.viewCaughtPokemon();
        assertTrue(pokemon.isEmpty());
    }

    @Test(expected = PokemonError.class)
    public void cannotCatchTheSamePokemonTwice() throws PokemonError {
        Pokemon pokemon = new NamedPokemon("charmander", "8", Arrays.asList("flame-body", "lava"));
        pokemonManager.catchPokemon(pokemon);
        pokemonManager.catchPokemon(pokemon);
    }

    @Test(expected = PokemonError.class)
    public void cannotCatchNullPokemon() throws PokemonError {
        Pokemon pokemon = Pokemon.NULL;
        pokemonManager.catchPokemon(pokemon);
    }

    @Test
    public void canSetAPokemonFree() throws PokemonError {
        Pokemon pokemon = new NamedPokemon("charmander", "8", Arrays.asList("flame-body", "lava"));
        pokemonManager.catchPokemon(pokemon);
        pokemonManager.setFree("charmander");
        List<Pokemon> caughtPokemon = pokemonManager.viewCaughtPokemon();
        assertTrue(caughtPokemon.isEmpty());
    }

    @Test(expected = PokemonError.class)
    public void throwsAnErrorIfPokemonIsNotCaught() throws PokemonError {
        pokemonManager.setFree("pikachu");
    }
}
