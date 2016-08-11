package pokemonmanager;

import java.util.List;

public interface StorageUnit {

    void save(Pokemon pokemon) throws PokemonError;

    List<Pokemon> getPokemon() throws PokemonError;

    void delete(String name) throws PokemonError;

}
