# Pokemon Manager

[![Build Status](https://travis-ci.org/MollieS/PokemonManager.svg?branch=master)](https://travis-ci.org/MollieS/PokemonManager) [![Coverage Status](https://coveralls.io/repos/github/MollieS/PokemonManager/badge.svg?branch=search)](https://coveralls.io/github/MollieS/PokemonManager?branch=search)


Pokemon Manager is a project allowing users to search for details about pokemon and save pokemon they have caught.

It retrieves data from the PokeApi and saves information to a postgres database.

This project is built in Java and Gradle.

### To run

This project is intended to be used with a [CLI Interface](https://github.com/MollieS/PokemonManagerCLI)

This package contains the functionality to retrieve information from the PokeApi and communicate with a postgres database.

### Test

This project has been test driven.  To run the tests, clone the repository with `git clone git@github.com:MollieS/PokemonManagerCLI.git`

Navigate into the repository with `cd PokemonManager`

Because the project uses a database, to run the tests you need to set up that database.  You can do this by running the command `gradle sql`

And run the tests with `gradle test`

