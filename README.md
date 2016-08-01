# Pokemon Manager

[![Build Status](https://travis-ci.org/MollieS/PokemonManager.svg?branch=master)](https://travis-ci.org/MollieS/PokemonManager) [![Coverage Status](https://coveralls.io/repos/github/MollieS/PokemonManager/badge.svg?branch=search)](https://coveralls.io/github/MollieS/PokemonManager?branch=search)


Pokemon Manager is a project allowing users to search for details about pokemon and save pokemon they have caught.

It retrieves data from the PokeApi and saves information to a postgres database.

This project is built in Java and Gradle.

## To run

This project is intended to be used with a [CLI Interface](https://github.com/MollieS/PokemonManagerCLI)

This package contains the functionality to retrieve information from the PokeApi and communicate with a postgres database.

The key functionality is held in two classes: PokemonFinder and PokemonManager.

### PokemonFinder

The PokemonFinder class deals with searching for pokemon from the PokeApi.

The PokemonFinder depends on an instance of PokemonSearch.

Because it only searches for name, creating an instance would look like this:

```
SearchEngine pokemonSearch = new PokemonSearch("http://pokeapi.co/api/v2/pokemon/");
PokemonFinder pokemonFinder = new PokemonFinder(pokemonSearch);
```

The PokemonFinder has one public method, find, which takes the name of a pokemon as an argument and returns a pokemon object.

In order to search the API all that is needed is the following:

```
Pokemon pokemon = pokemonFinder.find("Pikachu");

pokemon.getName();      // returns "pikachu"
pokemon.getHeight();    // returns "4"
pokemon.getAbilities(); // returns {"lightning-rod", "static"}

```

### PokemonManager

The PokemonManager class is responsible for managing caught pokemon.  

It depends on an instance of a StorageUnit.

To create an instance of PokemonManager you need the following:

```
StorageUnit dbManager = new DBManager("databaseURL", "username", "password", "driver");
PokemonManager pokemonManager = new PokemonManager(dbManager);
```

To save and retrieve from a database, the PokemonManager has two public methods:

```
Pokemon pikachu = pokemonFinder("pikachu");

pokemonManager.catchPokemon(pikachu);

List<Pokemon> caughtPokemon = pokemonManager.viewCaughtPokemon();
```

To set a pokemon free, PokemonManager has a set free method:

```
pokemonManager.setFree("pikachu");
```


## To Test

This project has been test driven.  To run the tests, clone the repository with `git clone git@github.com:MollieS/PokemonManagerCLI.git`

Navigate into the repository with `cd PokemonManager`

Because the project uses a database, to run the tests you need to set up that database.  You can do this by running the command `gradle sql`

And run the tests with `gradle test`

## UML

![UML](/assets/UML.png?raw=true "UML")

