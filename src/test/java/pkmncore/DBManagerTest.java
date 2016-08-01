package pkmncore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DBManagerTest {

    private DBManager dbManager = new DBManager("jdbc:postgresql://127.0.0.1:5432/pokemon_test", "test", "test", "org.postgresql.Driver");

    @Before
    public void setUp() throws Exception {
        Connection connection = getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS POKEMON (NAME VARCHAR(50) UNIQUE, HEIGHT VARCHAR(10));";
        String sql2 = "CREATE TABLE IF NOT EXISTS ABILITIES (NAME VARCHAR(50), ABILITY VARCHAR(50));";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.execute(sql2);
        statement.close();
        connection.close();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/pokemon_test", "test", "test");
    }

    @Test(expected = PokemonError.class)
    public void throwsErrorForInvalidDriver() throws Exception {
        DBManager dbManager = new DBManager("jdbc:postgresql://127.0.0.1:5432/pokemon_test", "test", "test", "not a driver");
        dbManager.getPokemon();
    }

    @Test(expected = PokemonError.class)
    public void throwsErrorifDatabaseErrors() throws Exception {
        dbManager.save("pikachu", "4", new String[]{"lightning-rod", "static"});
        Connection connection = getConnection();
        String sql = "DROP TABLE POKEMON;";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        dbManager.getPokemon();
        statement.close();
        connection.close();
    }

    @Test
    public void savesAPokemonDetails() throws Exception {
        dbManager.save("bulbasaur", "4", new String[]{"growth", "plant"});
        List<String> pokemon = dbManager.getPokemon().get(0);
        assertEquals("bulbasaur", pokemon.get(0));
        assertEquals("4", pokemon.get(1));
        assertEquals("growth", pokemon.get(2));
        assertEquals("plant", pokemon.get(3));
    }

    @Test
    public void canSaveManyPokemon() throws Exception {
        dbManager.save("charmander", "8", new String[]{"flame-body", "lava"});
        dbManager.save("squirtle", "6", new String[]{"bubble", "aqua-tail"});
        List<List<String>> allPokemon = dbManager.getPokemon();
        assertEquals(allPokemon.get(0).get(0), "charmander");
        assertEquals(allPokemon.get(1).get(0), "squirtle");
    }

    @Test(expected = PokemonError.class)
    public void cannotSaveSamePokemonTwice() throws Exception {
        dbManager.save("wartortle", "6", new String[]{"bubble", "aqua-tail"});
        dbManager.save("wartortle", "6", new String[]{"bubble", "aqua-tail"});
        List<List<String>> pokemon = dbManager.getPokemon();
        assertTrue(pokemon.size() == 1);
        assertTrue(pokemon.get(0).size() == 4);
    }

    @Test
    public void returnsEmptyListIfNoPokemonSaved() throws Exception {
        List<List<String>> pokemon = dbManager.getPokemon();
        assertTrue(pokemon.isEmpty());
    }

    @Test
    public void canDeleteAPokemon() throws Exception {
        dbManager.save("lapras", "24", new String[]{"ice-storm", "mist"});
        List<List<String>> caughtPokemon = dbManager.getPokemon();
        dbManager.delete("lapras");
        List<List<String>> pokemon = dbManager.getPokemon();
        assertTrue(pokemon.isEmpty());
    }

    @Test(expected = PokemonError.class)
    public void throwsAnErrorIfPokemonDeletedIsNotACaughtPokemon() throws PokemonError {
        dbManager.delete("bulbasaur");
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = getConnection();
        String sql = "DROP TABLE IF EXISTS POKEMON;";
        String sql2 = "DROP TABLE IF EXISTS ABILITIES;";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.execute(sql2);
        statement.close();
        connection.close();
    }
}
