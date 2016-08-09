package pokemonmanager;

import org.junit.Test;
import pokemonmanager.pokemon.NamedPokemon;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokemonTest {

    private NamedPokemon pokemon = new NamedPokemon("Pikachu", "1", Arrays.asList("lightning-rod", "static"));

    @Test
    public void hasAName() {
        assertEquals("Pikachu", pokemon.getName());
    }

    @Test
    public void hasAHeight() {
        assertEquals("1", pokemon.getHeight());
    }

    @Test
    public void hasAbilities() {
        assertTrue(pokemon.getAbilities().size() == 2);
        assertEquals("lightning-rod", pokemon.getAbilities().get(0));
        assertEquals("static", pokemon.getAbilities().get(1));
    }

    @Test
    public void canAddAnAbility() {
        pokemon.addAbility("electro");

        assertTrue(pokemon.getAbilities().size() == 3);
        assertEquals("lightning-rod", pokemon.getAbilities().get(0));
        assertEquals("static", pokemon.getAbilities().get(1));
        assertEquals("electro", pokemon.getAbilities().get(2));
    }

}
