package pokemonmanager.testfakes;

import pokemonmanager.Pokemon;
import pokemonmanager.PokemonError;
import pokemonmanager.StorageUnit;

import java.util.ArrayList;
import java.util.List;

public class StorageFake implements StorageUnit {

    private List<Pokemon> caughtPokemon = new ArrayList<>();

    public void save(Pokemon pokemon) throws PokemonError {
        checkIfCaught(pokemon);
        caughtPokemon.add(pokemon);
    }

    private void checkIfCaught(Pokemon pokemon) throws PokemonError {
        for (Pokemon poke : caughtPokemon) {
            if (poke.getName().equals(pokemon.getName())) {
                throw new PokemonError("Pokemon already caught");
            }
        }
    }

    public List<Pokemon> getPokemon() throws PokemonError {
        return caughtPokemon;
    }

    public void delete(String name) throws PokemonError {
        Pokemon toDelete = Pokemon.NULL;
        for (Pokemon pokemon : caughtPokemon) {
            if (pokemon.getName().equals(name)) {
                toDelete = pokemon;
            }
        }
        if (toDelete.equals(Pokemon.NULL)) {
            throw new PokemonError("Not caught");
        }
        caughtPokemon.remove(toDelete);
    }
}
