package pkmncore;

public class Main {

    public static void main(String[] args) {
        PokemonFinder finder = new PokemonFinder(new PokemonSearch("http://www.pokeapi.co/api/v2/pokemon/"));
        finder.find("pikachu");
    }
}
