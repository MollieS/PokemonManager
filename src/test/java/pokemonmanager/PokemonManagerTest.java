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
    private Pokemon pokemon = new NamedPokemon("charmander", "8", Arrays.asList("flame-body", "lava"));

    @Test
    public void canViewCaughtPokemon() throws PokemonError {
        pokemonManager.catchPokemon(pokemon);

        List<Pokemon> caughtPokemon = pokemonManager.viewCaughtPokemon();

        assertEquals("charmander", caughtPokemon.get(0).getName());
        assertEquals("8", caughtPokemon.get(0).getHeight());
        assertEquals("flame-body", caughtPokemon.get(0).getAbilities().get(0));
        assertEquals("lava", caughtPokemon.get(0).getAbilities().get(1));
    }

    @Test
    public void canAddManyPokemon() throws PokemonError {
        Pokemon pokemon2 = new NamedPokemon("squirtle", "8", Arrays.asList("water-body", "whirlpool"));

        pokemonManager.catchPokemon(pokemon);
        pokemonManager.catchPokemon(pokemon2);
        List<Pokemon> caughtPokemon = pokemonManager.viewCaughtPokemon();

        assertTrue(caughtPokemon.size() == 2);
        assertEquals("charmander", caughtPokemon.get(0).getName());
        assertEquals("squirtle", caughtPokemon.get(1).getName());
    }

    @Test
    public void ifNoPokemonAreCaughtItShowsAnEmptyList() throws PokemonError {
        List<Pokemon> pokemon = pokemonManager.viewCaughtPokemon();

        assertTrue(pokemon.isEmpty());
    }

    @Test(expected = PokemonError.class)
    public void cannotCatchTheSamePokemonTwice() throws PokemonError {
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
