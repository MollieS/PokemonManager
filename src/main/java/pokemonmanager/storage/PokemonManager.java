package pokemonmanager.storage;

import pokemonmanager.Pokemon;
import pokemonmanager.PokemonError;
import pokemonmanager.StorageUnit;

import java.util.List;

public class PokemonManager {

    private StorageUnit storage;

    public PokemonManager(StorageUnit storage) {
        this.storage = storage;
    }

    public void catchPokemon(Pokemon pokemon) throws PokemonError {
        checkForNullPokemon(pokemon);
        storage.save(pokemon);
    }

    public List<Pokemon> viewCaughtPokemon() throws PokemonError {
        return storage.getPokemon();
    }

    private void checkForNullPokemon(Pokemon pokemon) throws PokemonError {
        if (pokemon.equals(Pokemon.NULL)) {
            throw new PokemonError("This is not a pokemon");
        }
    }

    public void setFree(String name) throws PokemonError {
        storage.delete(name);
    }
}
