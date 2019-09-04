# README
# Poke Go Stats
A simple Android App that consumes off of [Rapid's Pokemon Go API](https://rapidapi.com/brianiswu/api/pokemon-go1) and organizes the given data to display to the user

# Built With
* [Dagger2](https://dagger.dev/) - For helping to connect Activities to Services and Dependency Injections with annotations
* [Gradle](https://gradle.org/) - Dependency management
* [Retrofit](https://square.github.io/retrofit/) - Framework code for consuming API calls and converting JSON
* [Android Room](https://developer.android.com/topic/libraries/architecture/room) - For internal SQLite database handling/cache
* [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel) - To handle database changes and persist code for views
* [Live Data](https://developer.android.com/topic/libraries/architecture/livedata) - To monitor changes to the database and assist in populating it to the views
* Kotlin

# Goals
Create a project that calls out to [RapidAPI](https://rapidapi.com/brianiswu/api/pokemon-go1) 's Pokemon Go Endpoints to consume and organize the data for the end user

Design layout thought up on Figma [here](https://www.figma.com/file/YqNCemCw1D11ra9U2rWBj0/Poke-Go-Stats?node-id=0%3A1)

# TODO
* Every endpoint has data involving each pokemon, organize all data from endpoints into an easy to read format
* Tab Layout for the following
    * Pokemon
    * Weather boost
    * Move data
* Implement Search feature for different Pokemon
* Drop down menu of diff stats for Pokemon like Max CP, Type, etc.
* Import images of Pokemon, Moves, and Weather conditions
