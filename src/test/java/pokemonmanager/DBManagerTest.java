package pokemonmanager;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pokemonmanager.pokemon.NamedPokemon;
import pokemonmanager.storage.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DBManagerTest {

    private DBManager dbManager = new DBManager("jdbc:postgresql://127.0.0.1:5432/pokemon_test", "test", "test", "org.postgresql.Driver");

    @Before
    public void setUp() throws Exception {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        createTables(statement);
        statement.close();
        connection.close();
    }

    private void createTables(Statement statement) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS POKEMON (NAME VARCHAR(50) UNIQUE, HEIGHT VARCHAR(10));";
        String sql2 = "CREATE TABLE IF NOT EXISTS ABILITIES (NAME VARCHAR(50), ABILITY VARCHAR(50));";
        statement.execute(sql);
        statement.execute(sql2);
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/pokemon_test", "test", "test");
    }

    @Test
    public void savesAPokemonNameAndHeight() throws PokemonError {
        NamedPokemon namedPokemon = new NamedPokemon("bulbasaur", "4", Arrays.asList("growth", "plant"));
        dbManager.save(namedPokemon);
        List<Pokemon> pokemon = dbManager.getPokemon();
        Pokemon poke = pokemon.get(0);
        assertEquals("bulbasaur", poke.getName());
        assertEquals("4", poke.getHeight());
    }

    @Test
    public void savesAPokemonAbilities() throws PokemonError {
        NamedPokemon namedPokemon = new NamedPokemon("bulbasaur", "4", Arrays.asList("growth", "plant"));
        dbManager.save(namedPokemon);
        List<Pokemon> pokemon = dbManager.getPokemon();
        Pokemon poke = pokemon.get(0);
        List<String> abilities = poke.getAbilities();
        assertEquals("growth", abilities.get(0));
        assertEquals("plant", abilities.get(1));
    }

    @Test
    public void canSaveManyPokemon() throws Exception {
        NamedPokemon namedPokemon = new NamedPokemon("bulbasaur", "4", Arrays.asList("growth", "plant"));
        NamedPokemon namedPokemon2 = new NamedPokemon("charmander", "8", Arrays.asList("flame-body", "lava"));
        dbManager.save(namedPokemon);
        dbManager.save(namedPokemon2);
        List<Pokemon> allPokemon = dbManager.getPokemon();
        assertEquals(allPokemon.get(0).getName(), "bulbasaur");
        assertEquals(allPokemon.get(1).getName(), "charmander");
    }

    @Test
    public void cannotSaveSamePokemonTwice() throws Exception {
        NamedPokemon namedPokemon = new NamedPokemon("wartortle", "6", Arrays.asList("bubble", "aqua-tail"));
        dbManager.save(namedPokemon);
        dbManager.save(namedPokemon);
        List<Pokemon> pokemon = dbManager.getPokemon();
        assertTrue(pokemon.size() == 1);
        assertTrue(pokemon.get(0).getAbilities().size() == 2);
    }

    @Test
    public void returnsEmptyListIfNoPokemonSaved() throws Exception {
        List<Pokemon> pokemon = dbManager.getPokemon();
        assertTrue(pokemon.isEmpty());
    }

    @Test
    public void canDeleteAPokemon() throws Exception {
        NamedPokemon pokemon = new NamedPokemon("lapras", "24", Arrays.asList("ice-storm", "mist"));
        dbManager.save(pokemon);
        dbManager.delete("lapras");
        List<Pokemon> caughtPokemon = dbManager.getPokemon();
        assertTrue(caughtPokemon.isEmpty());
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
