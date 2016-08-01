package pkmncore;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokemonManagerTest {

    private PokemonManager pokemonManager = new PokemonManager(new StorageFake());

    @Test
    public void canViewCaughtPokemon() throws PokemonError {
        Pokemon pokemon = new NamedPokemon("charmander", "8", new String[]{"flame-body", "lava"});
        pokemonManager.catchPokemon(pokemon);
        List<Pokemon> caughtPokemon = pokemonManager.viewCaughtPokemon();
        assertEquals("charmander", caughtPokemon.get(0).getName());
        assertEquals("8", caughtPokemon.get(0).getHeight());
        assertEquals("flame-body", caughtPokemon.get(0).getAbilities()[0]);
        assertEquals("lava", caughtPokemon.get(0).getAbilities()[1]);
    }

    @Test
    public void canAddManyPokemon() throws PokemonError {
        Pokemon pikachu = new NamedPokemon("pikachu", "4", new String[]{"static", "lightning-rod"});
        Pokemon squirtle = new NamedPokemon("squirtle", "8", new String[]{"water-body", "whirlpool"});
        pokemonManager.catchPokemon(pikachu);
        pokemonManager.catchPokemon(squirtle);
        List<Pokemon> pokemon = pokemonManager.viewCaughtPokemon();
        assertEquals("pikachu", pokemon.get(0).getName());
        assertEquals("squirtle", pokemon.get(1).getName());
        assertTrue(pokemon.size() == 2);
    }

    @Test
    public void ifNoPokemonAreCaughtItShowsAnEmptyList() {
        List<Pokemon> pokemon = pokemonManager.viewCaughtPokemon();
        assertTrue(pokemon.isEmpty());
    }

    @Test(expected = PokemonError.class)
    public void cannotCatchTheSamePokemonTwice() throws PokemonError {
        Pokemon pokemon = new NamedPokemon("charmander", "8", new String[]{"flame-body", "lava"});
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
        Pokemon pokemon = new NamedPokemon("charmander", "8", new String[]{"flame-body", "lava"});
        pokemonManager.catchPokemon(pokemon);
        pokemonManager.setFree("charmander");
        List<Pokemon> caughtPokemon = pokemonManager.viewCaughtPokemon();
        assertTrue(caughtPokemon.isEmpty());
    }

    @Test
    public void throwsAnErrorIfPokemonIsNotCaught() {
    }
}
