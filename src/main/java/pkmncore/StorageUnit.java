package pkmncore;

import java.util.List;

public interface StorageUnit {

    void save(String name, String height, String[] abilities) throws PokemonError;

    List<List<String>> getPokemon() throws PokemonError;

    void delete(String name) throws PokemonError;
}
