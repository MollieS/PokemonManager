package pkmncore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    final private String dbURL;
    final private String username;
    final private String password;

    public DBManager(String dbURL, String username, String password) {
        this.dbURL = dbURL;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws PokemonError {
        setDriver();
        return connectToDB();
    }

    public void save(String name, String height, String[] abilities) throws PokemonError {
        Connection connection = getConnection();
        Statement statement = createStatement(connection);
        saveNameAndHeight(name, height, statement);
        saveAbilites(name, abilities, statement);
        closeConnections(connection, statement);
    }


    public List<List<String>> getPokemon() throws PokemonError {
        Connection connection = getConnection();
        Statement statement = createStatement(connection);
        String sql = "SELECT * FROM POKEMON;";
        List<List<String>> pokemon = listPokemon(connection, getCaughtPokemon(statement, sql));
        closeConnections(connection, statement);
        return pokemon;
    }

    private List<List<String>> listPokemon(Connection connection, ResultSet caughtPokemon) throws PokemonError{
        List<List<String>> allPokemon = new ArrayList<List<String>>();
        try {
            while (caughtPokemon.next()) {
                List<String> pokemon = new ArrayList<String>();
                pokemon.add(caughtPokemon.getString("name"));
                pokemon.add(caughtPokemon.getString("height"));
                getAbilities(pokemon, connection);
                allPokemon.add(pokemon);
            }
        } catch (SQLException e) {
            throw new PokemonError("Error listing caught pokemon: " + e.getMessage());
        }
        return allPokemon;
    }

    private ResultSet getCaughtPokemon(Statement statement, String sql) throws PokemonError {
        ResultSet pokemon;
        try {
            pokemon = statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new PokemonError("Error getting data: " + e.getMessage());
        }
        return pokemon;
    }

    private void getAbilities(List<String> pokemon, Connection connection) throws PokemonError {
        Statement statement = createStatement(connection);
        String sql = "SELECT * FROM ABILITIES WHERE (name) = '" + pokemon.get(0) + "';";
        ResultSet abilities = getCaughtPokemon(statement, sql);
        listAbilities(pokemon, abilities);
    }

    private void listAbilities(List<String> pokemon, ResultSet abilities) throws PokemonError {
        try {
            while (abilities.next()) {
                pokemon.add(abilities.getString("ability"));
            }
        } catch (SQLException e) {
            throw new PokemonError("Error listing abilities: " + e.getMessage());
        }
    }

    private void setDriver() throws PokemonError {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new PokemonError("Error getting driver: " + e.getMessage());
        }
    }

    private Connection connectToDB() throws PokemonError {
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            throw new PokemonError("Error getting db connection: " + e.getMessage());
        }
        return connection;
    }

    private Statement createStatement(Connection connection) throws PokemonError {
        Statement statement;
        try {
            statement =  connection.createStatement();
        } catch (SQLException e) {
            throw new PokemonError("Error creating statement: " + e.getMessage());
        }
        return statement;
    }

    private void executeStatement(Statement statement, String sql) throws PokemonError {
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new PokemonError("Error executing statement: " + e.getMessage());
        }
    }

    private void closeConnections(Connection connection, Statement statement) throws PokemonError {
        try {
            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new PokemonError("Error closing connections: " + e.getMessage());
        }
    }

    private void saveNameAndHeight(String name, String height, Statement statement) throws PokemonError {
        String sql = "INSERT INTO POKEMON (name, height) VALUES ('" + name + "', '" + height + "');";
        executeStatement(statement, sql);
    }

    private void saveAbilites(String name, String[] abilities, Statement statement) throws PokemonError {
        for (String ability : abilities) {
            String sql = "INSERT INTO ABILITIES (name, ability) VALUES ('" + name + "', '" + ability + "');";
            executeStatement(statement, sql);
        }
    }
}
