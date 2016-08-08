package pokemonmanager.storage;

import pokemonmanager.PokemonError;
import pokemonmanager.StorageUnit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DBManager implements StorageUnit {

    final private String dbURL;
    final private String username;
    final private String password;
    final private String driver;

    public DBManager(String dbURL, String username, String password, String driver) {
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
        this.driver = driver;
    }

    public void save(String name, String height, String[] abilities) throws PokemonError {
        int saved = runInConnection(statement -> saveNameAndHeight(name, height, statement));
        if (saved > 0) {
            runInConnection(statement -> saveAbilites(name, abilities, statement));
        }
    }

    public void delete(String name) throws PokemonError {
        int deleted = runInConnection(statement -> deletePokemon(name, statement));
        checkIfCaught(deleted);
    }

    public List<List<String>> getPokemon() throws PokemonError {
        List<List<String>> pokemon = runInConnection(statement -> getPokemonList(statement));
        return runInConnection(statement -> getPokemonAbilities(pokemon, statement));
    }

    private List<List<String>> getPokemonList(Statement statement) {
        String sql = "SELECT * FROM POKEMON;";
        ResultSet caughtPokemon = null;
        try {
            caughtPokemon = statement.executeQuery(sql);
            return getPokemonNames(caughtPokemon);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    private List<List<String>> getPokemonAbilities(List<List<String>> caughtPokemon, Statement statement) {
        for (List<String> pokemon : caughtPokemon) {
            String sql = "SELECT * FROM ABILITIES WHERE (name) = '" + pokemon.get(0) + "';";
            ResultSet abilities = null;
            try {
                abilities = statement.executeQuery(sql);
                listAbilities(pokemon, abilities);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return caughtPokemon;
    }

    private List<String> listAbilities(List<String> pokemon, ResultSet abilities) throws SQLException {
        while (abilities.next()) {
            pokemon.add(abilities.getString("ability"));
        }
        return pokemon;
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

    private int saveAbilites(String name, String[] abilities, Statement statement) {
        int saved = 0;
        for (String ability : abilities) {
            String sql = "INSERT INTO ABILITIES (name, ability) VALUES ('" + name + "', '" + ability + "');";
            saved = executeUpdate(statement, sql);
        }
        return saved;
    }
}
