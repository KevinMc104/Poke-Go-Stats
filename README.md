# README
## Poke Go Stats
A simple Android App that consumes off of [Rapid's Pokemon Go API](https://rapidapi.com/brianiswu/api/pokemon-go1) and organizes the given data to display to the user

## Built With
* [Dagger2](https://dagger.dev/) - For helping to connect Activities to Services and Dependency Injections with annotations
* [Gradle](https://gradle.org/) - Dependency management
* [Retrofit](https://square.github.io/retrofit/) - Framework code for consuming API calls and converting JSON
* [Android Room](https://developer.android.com/topic/libraries/architecture/room) - A wrapper on SQLite database handling/cache for API Data
* [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel) - To handle database changes and persist code for views
* [Live Data](https://developer.android.com/topic/libraries/architecture/livedata) - To monitor changes to the database and assist in populating it to the views
* [Material Design Icons](https://github.com/konifar/android-material-design-icon-generator-plugin) - Grants access to a large database of icons from [Material.io](https://material.io/resources/icons/?style=baseline) to use in your Android Studio Project
* Kotlin

## How it works
* JSON Data of all the endpoints and Pseudo Data Models [here](https://pastebin.com/UXTUaGDX)
* SQL Test Code [here](https://pastebin.com/j9iQZfRN)
* Design layout thought up on Figma [here](https://www.figma.com/file/YqNCemCw1D11ra9U2rWBj0/Poke-Go-Stats?node-id=0%3A1)

## Goals
* Create a project that calls out to [RapidAPI](https://rapidapi.com/brianiswu/api/pokemon-go1)'s Pokemon Go Endpoints data to consume, cache, organize, and display for the end user
* Publish finished product to Google Play Store

## TODO
* Implement Search/Sort feature for Pokemon and Moves
    * Sort by Max CP+Type, Pokemon Types, Move Types
    * ~~Sort by Max CP, Pokemon Name, Move Name, Move Power~~
    * ~~Searchable Pokemon Name, Pokemon Types~~
    * ~~Searchable Move Name, Move Types~~
* Import images of Pokemon, Moves, and Weather conditions as drawables
* Tweak colors/styles to make the look/feel for UI better
* Add detailed documentation of how the app works and present on Twitch live
    * Include screenshots of code and of the running app in written documentation
    * Add live video recording to YouTube to be shared
* ~~Add headers to Pokemon/Move Lists~~
    * ~~Pokemon Name, Max CP, Pokemon Type~~
    * ~~Move Name, Power, Move Type~~
* ~~Implement all the endpoints from [RapidAPI](https://rapidapi.com/brianiswu/api/pokemon-go1)'s Pokemon Go Endpoints and organize/insert into Android Room for use in Repository for ViewModel classes~~
* ~~View Models for each group of Activities/Fragments~~
* ~~Inject Service into Repository directly~~
* ~~Tab Layout for the following~~
    * ~~All Pokemon View~~
    * ~~Detailed Pokemon View~~
    * ~~Detailed Pokemon Weather Boost View~~
    * ~~All Move Data View~~
    * ~~Detailed Move Data View~~
* ~~Retrofit Service classes to call to [RapidAPI](https://rapidapi.com/brianiswu/api/pokemon-go1) and gather data~~
* ~~Use GSON Converter to convert JSON to data classes to easily use with inserts into Android Room Database Tables(Entities)~~
* ~~Populate Android Room Databases with [RapidAPI](https://rapidapi.com/brianiswu/api/pokemon-go1) data~~
* ~~Change it so data is inserted into the database once on Open~~
* ~~Live Data Objects to gather Android Room Data~~
* ~~Fix issues with app crashing when rotated or closed/restarted (Need to implement things like onResume/onPause etc.)~~

## Extra Goals
* Will likely need to get this data from a different API
    * Best Attack and Defense Move Combos for each Pokemon
    * Add Pokemon Evolutions
* Add Database migration when updates roll around for app
* ~~Add Timed Death on Database via column in database for data~~
