package pkmncore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    private Connection getConnection() throws PokemonError, SQLException {
        setDriver();
        return connectToDB();
    }

    public void save(String name, String height, String[] abilities) throws PokemonError {
        savePokemonDetails(name, height, abilities);
    }

    public List<List<String>> getPokemon() throws PokemonError {
        return getAllPokemon();
    }

    public void delete(String name) throws PokemonError {
        ResultSet pokemon = null;
        int deleted = 0;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            deleted = deletePokemon(name, statement);
            closeConnections(connection, statement);
        } catch (SQLException e) {
            e.getMessage();
        }
        checkIfCaught(deleted);
    }

    private void checkIfCaught(int deleted) throws PokemonError {
        if (deleted < 1) {
            throw new PokemonError("Not caught!");
        }
    }

    private int deletePokemon(String name, Statement statement) throws SQLException {
        String sql = "DELETE FROM POKEMON WHERE NAME = '" + name + "'";
        String sql2 = "DELETE FROM ABILITIES WHERE NAME = '" + name + "'";
        int deleted = statement.executeUpdate(sql);
        statement.executeUpdate(sql2);
        return deleted;
    }

    private void savePokemonDetails(String name, String height, String[] abilities) throws PokemonError {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            saveNameAndHeight(name, height, statement);
            saveAbilites(name, abilities, statement);
            closeConnections(connection, statement);
        } catch (SQLException e) {
            throw new PokemonError(name + " has already been caught!");
        }
    }

    private List<List<String>> getAllPokemon() throws PokemonError {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM POKEMON;";
            List<List<String>> pokemon = listPokemon(connection, statement.executeQuery(sql));
            closeConnections(connection, statement);
            return pokemon;
        } catch (SQLException e) {
            throw new PokemonError("Something is wrong with the database! " + e.getMessage());
        }
    }

    private List<List<String>> listPokemon(Connection connection, ResultSet caughtPokemon) throws SQLException {
        List<List<String>> allPokemon = new ArrayList<List<String>>();
        while (caughtPokemon.next()) {
            List<String> pokemon = new ArrayList<String>();
            pokemon.add(caughtPokemon.getString("name"));
            pokemon.add(caughtPokemon.getString("height"));
            pokemon = getAbilities(pokemon, connection);
            allPokemon.add(pokemon);
        }
        return allPokemon;
    }

    private List<String> getAbilities(List<String> pokemon, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM ABILITIES WHERE (name) = '" + pokemon.get(0) + "';";
        ResultSet abilities = statement.executeQuery(sql);
        pokemon = listAbilities(pokemon, abilities);
        return pokemon;
    }

    private List<String> listAbilities(List<String> pokemon, ResultSet abilities) throws SQLException {
        while (abilities.next()) {
            pokemon.add(abilities.getString("ability"));
        }
        return pokemon;
    }

    private void setDriver() throws PokemonError {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new PokemonError("Invalid driver " + driver + "\n" + e.getMessage());
        }
    }

    private Connection connectToDB() throws SQLException {
        return DriverManager.getConnection(dbURL, username, password);
    }

    private void closeConnections(Connection connection, Statement statement) throws SQLException {
        connection.close();
        statement.close();
    }

    private void saveNameAndHeight(String name, String height, Statement statement) throws SQLException {
        String sql = "INSERT INTO POKEMON (name, height) VALUES ('" + name + "', '" + height + "');";
        statement.execute(sql);
    }

    private void saveAbilites(String name, String[] abilities, Statement statement) throws SQLException {
        for (String ability : abilities) {
            String sql = "INSERT INTO ABILITIES (name, ability) VALUES ('" + name + "', '" + ability + "');";
            statement.execute(sql);
        }
    }
}
