package pkmncore.testfakes;

import pkmncore.PokemonError;
import pkmncore.StorageUnit;

import java.util.ArrayList;
import java.util.List;

public class StorageFake implements StorageUnit {

    private List<List<String>> pokemon = new ArrayList<List<String>>();

    public void save(String name, String height, String[] abilities) throws PokemonError {
        checkIfCaught(name);
        List<String> details = addNameAndHeight(name, height);
        addAbilites(abilities, details);
        pokemon.add(details);
    }

    private void checkIfCaught(String name) throws PokemonError {
        for (List<String> poke : pokemon) {
            if (poke.get(0).equals(name)) {
                throw new PokemonError("Pokemon already caught");
            }
        }
    }

    public List<List<String>> getPokemon() throws PokemonError {
        if (pokemon.isEmpty()) {
            throw new PokemonError("No pokemon!");
        }
        return pokemon;
    }

    public void delete(String name) throws PokemonError {
        int toDelete = -10;
        for (int i = 0; i < pokemon.size(); i++) {
            if (name.equals(pokemon.get(i).get(0))) {
                toDelete = i;
            }
        }
        pokemon.remove(toDelete);
    }

    private void addAbilites(String[] abilities, List<String> details) {
        for (String ability : abilities) {
            details.add(ability);
        }
    }

    private List<String> addNameAndHeight(String name, String height) {
        List<String> details = new ArrayList<String>();
        details.add(name);
        details.add(height);
        return details;
    }

}
