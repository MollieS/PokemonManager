package pokemonmanager.storage;

import pokemonmanager.Pokemon;
import pokemonmanager.PokemonError;
import pokemonmanager.StorageUnit;
import pokemonmanager.pokemon.NamedPokemon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DBManager implements StorageUnit {

    private final String dbURL;
    private final String username;
    private final String password;
    private final String driver;

    public DBManager(String dbURL, String username, String password, String driver) {
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    public void save(Pokemon pokemon) throws PokemonError {
        runInConnection(statement -> savePokemon(statement, pokemon));
    }

    private int savePokemon(Statement statement, Pokemon pokemon) {
        String name = pokemon.getName();
        String height = pokemon.getHeight();
        List<String> abilities = pokemon.getAbilities();
        String sql = "INSERT INTO POKEMON (name, height) VALUES ('" + name + "', '" + height + "');";
        int saved = executeUpdate(statement, sql);
        if (saved > 0) {
            saveAbilites(name, abilities, statement);
        }
        return saved;
    }

    public List<Pokemon> getPokemon() throws PokemonError {
        List<Pokemon> pokemon = runInConnection(statement -> getPokemonNameAndHeight(statement));
        List<Pokemon> caughtPokemon = runInConnection(statement -> getPokemonAbilities(pokemon, statement));
        return caughtPokemon;
    }

    private List<Pokemon> getPokemonNameAndHeight(Statement statement) {
        String sql = "SELECT * FROM POKEMON;";
        List<Pokemon> caughtPokemon = new ArrayList<>();
        Pokemon pokemon;
        try {
            ResultSet allPokemon = statement.executeQuery(sql);
            while (allPokemon.next()) {
                String name = allPokemon.getString("name");
                String height = allPokemon.getString("height");
                pokemon = new NamedPokemon(name, height, new ArrayList<>());
                caughtPokemon.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return caughtPokemon;
    }

    private List<Pokemon> getPokemonAbilities(List<Pokemon> caughtPokemon, Statement statement) {
        for (Pokemon pokemon : caughtPokemon) {
            String sql = "SELECT * FROM ABILITIES WHERE NAME = '" + pokemon.getName() + "';";
            try {
                ResultSet abilities = statement.executeQuery(sql);
                while (abilities.next()) {
                    pokemon.addAbility(abilities.getString("ability"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return caughtPokemon;
    }

    public void delete(String name) throws PokemonError {
        int deleted = runInConnection(statement -> deletePokemon(name, statement));
        checkIfCaught(deleted);
    }

    private <T> T runInConnection(Function<Statement, T> operation) throws PokemonError {
        T returnValue = null;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            returnValue = operation.apply(statement);

            closeConnections(connection, statement);
        } catch (SQLException e) {
            throw new PokemonError(e.getMessage());
        } finally {
            return returnValue;
        }
    }

    private List<List<String>> getPokemonList(Statement statement) {
        String sql = "SELECT * FROM POKEMON;";
        List<List<String>> pokemon = null;
        try {
            ResultSet caughtPokemon = statement.executeQuery(sql);
            pokemon = getPokemonNames(caughtPokemon);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemon;
    }

    private List<List<String>> getPokemonNames(ResultSet caughtPokemon) throws SQLException {
        List<List<String>> allPokemon = new ArrayList<>();
        while (caughtPokemon.next()) {
            List<String> pokemon = new ArrayList<>();
            pokemon.add(caughtPokemon.getString("name"));
            pokemon.add(caughtPokemon.getString("height"));
            allPokemon.add(pokemon);
        }
        return allPokemon;
    }


    private List<String> listAbilities(List<String> pokemon, ResultSet abilities) throws SQLException {
        while (abilities.next()) {
            pokemon.add(abilities.getString("ability"));
        }
        return pokemon;
    }


    private void checkIfCaught(int deleted) throws PokemonError {
        if (deleted < 1) {
            throw new PokemonError("Not caught!");
        }
    }

    private int deletePokemon(String name, Statement statement) {
        String sql = "DELETE FROM POKEMON WHERE NAME = '" + name + "'";
        String sql2 = "DELETE FROM ABILITIES WHERE NAME = '" + name + "'";
        int deleted = executeUpdate(statement, sql);
        executeUpdate(statement, sql2);
        return deleted;
    }

    private void setDriver() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
    }

    private Connection connectToDB() {
        try {
            return DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    private void closeConnections(Connection connection, Statement statement) throws SQLException {
        connection.close();
        statement.close();
    }

    private int saveNameAndHeight(String name, String height, Statement statement) {
        String sql = "INSERT INTO POKEMON (name, height) VALUES ('" + name + "', '" + height + "');";
        return executeUpdate(statement, sql);
    }

    private Connection getConnection() {
        setDriver();
        return connectToDB();
    }

    private int executeUpdate(Statement statement, String sql) {
        int saved = 0;
        try {
            saved = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saved;
    }

    private int saveAbilites(String name, List<String> abilities, Statement statement) {
        int saved = 0;
        for (String ability : abilities) {
            String sql = "INSERT INTO ABILITIES (name, ability) VALUES ('" + name + "', '" + ability + "');";
            saved = executeUpdate(statement, sql);
        }
        return saved;
    }

}
