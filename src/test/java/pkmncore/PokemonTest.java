package pkmncore;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PokemonTest {

    private Pokemon pokemon = new Pokemon("Pikachu", 1, new String[]{"lightning-rod", "static"});

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
        assertEquals(new String[]{"lightning-rod", "static"}, pokemon.getAbilities());
    }
}
