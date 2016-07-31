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
            getDetails(caughtPokemon, pokemonDetails);
        }
        return caughtPokemon;
    }

    private void getDetails(List<Pokemon> caughtPokemon, List<String> pokemonDetails) {
        String name = pokemonDetails.remove(0);
        String height = pokemonDetails.remove(0);
        String[] abilities = getAbilities(pokemonDetails);
        Pokemon pokemon = new NamedPokemon(name, height, abilities);
        caughtPokemon.add(pokemon);
    }

    private String[] getAbilities(List<String> pokemonDetails) {
        String[] abilities = new String[pokemonDetails.size()];
        for (int i = 0; i < pokemonDetails.size(); i++) {
            abilities[i] = pokemonDetails.get(i);
        }
        return abilities;
    }

    private List<List<String>> getPokemon() {
        List<List<String>> pokemon = new ArrayList<List<String>>();
        try {
            pokemon = storage.getPokemon();
            return pokemon;
        } catch (PokemonError pokemonError) {
            return pokemon;
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
}
