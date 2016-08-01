package pkmncore;

import java.util.ArrayList;
import java.util.List;

public class PokemonManager {

    private StorageUnit storage;

    public PokemonManager(StorageUnit storage) {
        this.storage = storage;
    }

    public void catchPokemon(Pokemon pokemon) throws PokemonError {
        checkForNullPokemon(pokemon);
        savePokemon(pokemon);
    }

    public List<Pokemon> viewCaughtPokemon() {
        List<Pokemon> caughtPokemon = new ArrayList<Pokemon>();
        List<List<String>> allPokemon = getPokemon();
        for (List<String> pokemonDetails : allPokemon) {
            caughtPokemon.add(createPokemon(pokemonDetails));
        }
        return caughtPokemon;
    }

    private Pokemon createPokemon(List<String> pokemonDetails) {
        String name = pokemonDetails.remove(0);
        String height = pokemonDetails.remove(0);
        String[] abilities = getAbilities(pokemonDetails);
        return new NamedPokemon(name, height, abilities);
    }

    private String[] getAbilities(List<String> pokemonDetails) {
        String[] abilities = new String[pokemonDetails.size()];
        for (int i = 0; i < pokemonDetails.size(); i++) {
            abilities[i] = pokemonDetails.get(i);
        }
        return abilities;
    }

    private List<List<String>> getPokemon() {
        try {
            return storage.getPokemon();
        } catch (PokemonError pokemonError) {
            return new ArrayList<List<String>>();
        }
    }

    private void savePokemon(Pokemon pokemon) throws PokemonError {
        try {
            storage.save(pokemon.getName(), pokemon.getHeight(), pokemon.getAbilities());
        } catch (PokemonError pokemonError) {
            throw new PokemonError(pokemon.getName() +  " has already been caught!" + pokemonError.getMessage());
        }
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
