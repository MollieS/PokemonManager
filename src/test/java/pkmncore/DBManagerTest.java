package pkmncore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DBManagerTest {

    private DBManager dbManager = new DBManager("jdbc:postgresql://127.0.0.1:5432/pokemon_test", "test", "test", "org.postgresql.Driver");

    @After
    public void tearDown() throws Exception {
        Connection connection = dbManager.getConnection();
        String sql = "DROP TABLE POKEMON;";
        String sql2 = "DROP TABLE ABILITIES;";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.execute(sql2);
        statement.close();
        connection.close();
    }

    @Before
    public void setUp() throws Exception {
        Connection connection = dbManager.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS POKEMON (NAME VARCHAR(50) UNIQUE, HEIGHT VARCHAR(10));";
        String sql2 = "CREATE TABLE IF NOT EXISTS ABILITIES (NAME VARCHAR(50), ABILITY VARCHAR(50));";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.execute(sql2);
        statement.close();
        connection.close();
    }

    @Test
    public void connectsToDatabase() throws Exception {
        Connection connection = dbManager.getConnection();
        assertTrue(connection != null);
        connection.close();
    }

    @Test(expected = PokemonError.class)
    public void throwsErrorForInvalidDriver() throws PokemonError, SQLException {
        DBManager dbManager = new DBManager("jdbc:postgresql://127.0.0.1:5432/pokemon_test", "test", "test", "not a driver");
        Connection connection = dbManager.getConnection();
    }

    @Test
    public void savesAPokemonDetails() throws Exception {
        dbManager.save("pikachu", "4", new String[]{"lightning-rod", "static"});
        List<String> pokemon = dbManager.getPokemon().get(0);
        assertEquals("pikachu", pokemon.get(0));
        assertEquals("4", pokemon.get(1));
        assertEquals("lightning-rod", pokemon.get(2));
        assertEquals("static", pokemon.get(3));
    }

    @Test
    public void canSaveManyPokemon() throws Exception {
        dbManager.save("pikachu", "4", new String[]{"lightning-rod", "static"});
        dbManager.save("bulbasaur", "4", new String[]{"growth", "plant"});
        List<List<String>> allPokemon = dbManager.getPokemon();
        assertEquals(allPokemon.get(0).get(0), "pikachu");
        assertEquals(allPokemon.get(1).get(0), "bulbasaur");
    }

    @Test(expected = PokemonError.class)
    public void cannotSaveSamePokemonTwice() throws Exception {
        dbManager.save("pikachu", "4", new String[]{"lightning-rod", "static"});
        dbManager.save("pikachu", "4", new String[]{"lightning-rod", "static"});
        List<List<String>> pokemon = dbManager.getPokemon();
        assertTrue(pokemon.size() == 1);
        assertTrue(pokemon.get(0).size() == 4);
    }

    @Test
    public void returnsEmptyListIfNoPokemonSaved() throws PokemonError {
        List<List<String>> pokemon = dbManager.getPokemon();
        assertTrue(pokemon.isEmpty());
    }
}
