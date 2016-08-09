package pokemonmanager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pokemonmanager.pokemon.NamedPokemon;
import pokemonmanager.storage.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DBManagerTest {

    private DBManager dbManager = new DBManager("jdbc:postgresql://127.0.0.1:5432/pokemon_test", "test", "test", "org.postgresql.Driver");
    private Pokemon pokemon = new NamedPokemon("bulbasaur", "4", Arrays.asList("growth", "plant"));

    @Before
    public void setUp() throws Exception {
        connectToDB(statement -> cleanDB(statement));
    }

    @After
    public void tearDown() throws Exception {
        connectToDB(statement -> dropTables(statement));
    }

    @Test
    public void savesAPokemonNameAndHeight() throws PokemonError {
        dbManager.save(pokemon);

        Pokemon pokemon = dbManager.getPokemon().get(0);

        assertEquals("bulbasaur", pokemon.getName());
        assertEquals("4", pokemon.getHeight());
    }

    @Test
    public void savesAPokemonAbilities() throws PokemonError {
        dbManager.save(pokemon);

        Pokemon pokemon = dbManager.getPokemon().get(0);
        List<String> abilities = pokemon.getAbilities();

        assertEquals("growth", abilities.get(0));
        assertEquals("plant", abilities.get(1));
    }

    @Test
    public void canSaveManyPokemon() throws Exception {
        NamedPokemon namedPokemon = new NamedPokemon("charmander", "8", Arrays.asList("flame-body", "lava"));
        dbManager.save(pokemon);
        dbManager.save(namedPokemon);

        List<Pokemon> allPokemon = dbManager.getPokemon();

        assertEquals(allPokemon.get(0).getName(), "bulbasaur");
        assertEquals(allPokemon.get(1).getName(), "charmander");
    }

    @Test
    public void cannotSaveSamePokemonTwice() throws Exception {
        dbManager.save(pokemon);
        dbManager.save(pokemon);

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
        dbManager.save(pokemon);

        dbManager.delete("bulbasaur");
        List<Pokemon> caughtPokemon = dbManager.getPokemon();

        assertTrue(caughtPokemon.isEmpty());
    }

    @Test(expected = PokemonError.class)
    public void throwsAnErrorIfPokemonDeletedIsNotACaughtPokemon() throws PokemonError {
        dbManager.delete("bulbasaur");
    }

    private void closeConnections(Connection connection, Statement statement) throws SQLException {
        statement.close();
        connection.close();
    }

    private void connectToDB(Function<Statement, Boolean> operation) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            operation.apply(statement);
            closeConnections(connection, statement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean dropTables(Statement statement) {
        String sql = "DROP TABLE IF EXISTS POKEMON;";
        String sql2 = "DROP TABLE IF EXISTS ABILITIES;";
        boolean dropped = false;
        try {
            dropped = statement.execute(sql);
            statement.execute(sql2);
        } catch (SQLException e) {
            e.getMessage();
        }
        return dropped;
    }

    private boolean cleanDB(Statement statement) {
        boolean cleaned = false;
        try {
            cleaned = dropTables(statement);
            createTables(statement);
        } catch (SQLException e) {
            e.getMessage();
        }
        return cleaned;
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

}
