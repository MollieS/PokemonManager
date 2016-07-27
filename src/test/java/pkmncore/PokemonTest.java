package pkmncore;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PokemonTest {

    private Pokemon pokemon = new Pokemon("Pikachu", 1, "lightning-rod");

    @Test
    public void hasAName() {
        assertEquals("Pikachu", pokemon.getName());
    }

    @Test
    public void hasAHeight() {
        assertEquals(7, pokemon.getHeight());
    }

    @Test
    public void hasAnAbility() {
        assertEquals("lightning-rod", pokemon.getAbility());
    }
}
