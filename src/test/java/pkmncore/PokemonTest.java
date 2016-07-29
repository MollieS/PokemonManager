package pkmncore;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokemonTest {

    private NamedPokemon pokemon = new NamedPokemon("Pikachu", 1, new String[]{"lightning-rod", "static"});

    @Test
    public void hasAName() {
        assertEquals("Pikachu", pokemon.getName());
    }

    @Test
    public void hasAHeight() {
        assertEquals(1, pokemon.getHeight());
    }

    @Test
    public void hasAbilities() {
        assertEquals("lightning-rod", pokemon.getAbilities()[0]);
        assertEquals("static", pokemon.getAbilities()[1]);
        assertTrue(pokemon.getAbilities().length == 2);
    }
}
