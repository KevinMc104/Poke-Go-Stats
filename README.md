# README
## Poke Go Stats
A simple Android App that consumes off of [Rapid's Pokemon Go API](https://rapidapi.com/brianiswu/api/pokemon-go1) and organizes the given data to display to the user

## Goals
* ✔ Create a project that calls out to [RapidAPI](https://rapidapi.com/brianiswu/api/pokemon-go1)'s Pokemon Go Endpoints data to consume, cache, organize, and display for the end user
* Publish finished product to Google Play Store
* Present project live on Twitch and save video to YouTube/LinkedIn

## Built With
* [Dagger2](https://dagger.dev/) - For helping to connect Activities to Services and Dependency Injections with annotations
* [Gradle](https://gradle.org/) - Dependency management
* [Retrofit](https://square.github.io/retrofit/) - Framework code for consuming API calls and converting JSON
* [Android Room](https://developer.android.com/topic/libraries/architecture/room) - A wrapper on SQLite database handling/cache for API Data
* [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel) - To handle database changes and persist code for views
* [Live Data](https://developer.android.com/topic/libraries/architecture/livedata) - To monitor changes to the database and assist in populating it to the views
* [Material Design Icons](https://github.com/konifar/android-material-design-icon-generator-plugin) - Grants access to a large database of icons from [Material.io](https://material.io/resources/icons/?style=baseline) to use in your Android Studio Project
* Kotlin

# How it works
* JSON Data of all the endpoints and Pseudo Data Models [here](https://pastebin.com/UXTUaGDX)
* SQL Test Code [here](https://pastebin.com/j9iQZfRN)
* Design layout thought up on Figma [here](https://www.figma.com/file/YqNCemCw1D11ra9U2rWBj0/Poke-Go-Stats?node-id=0%3A1)
* Pokemon image file names must be all lowercase and in the format of **``pokemon_{pokemonID}_{pokemonName}_{pokemonForm}``** to be added to this app
    * {pokemonId} & {pokemonName} are required
    * {pokemonForm} is optional(leave blank is normal form)
    * Ie. "pokemon_1_bulbasaur" or "pokemon_1_rattata_alola"

# Architectural diagrams
## Overall Design
![resources](https://docs.google.com/drawings/d/e/2PACX-1vSEKw264XJhoInCJHUuij7iSSmJ8tyjFzySChDGJnPGOkDQ_-h8Y2UwEptEt68GOYCeucWDJbLmNrgS/pub?w=960&h=720)
The Error Activity lives on it's own. When server errors happen in Retrofit calls(Pokemon Go API Service) to the Pokemon Go Rapid API Servers, such as 404, 500, etc. It'll throw an error that is then caught in the error activity and displayed to the user. It'll tell the user the error number with a message saying that there was a problem with data retrieval. There is a button as seen from the screenshot below, that allows them to retry the server calls and try to downloaded the data again through essentially restarting the app through the main activity.
![Imgur](https://i.imgur.com/YmYbhYE.png)

## Database Tables
![resources](https://docs.google.com/drawings/d/e/2PACX-1vRTbpIDnDpIvrZEj814Dh2E94qJx4oKh_xq_Q_68fcrSgaQR0t0kNgbMYJteFKPosmkW6tlTkrNC5dx/pub?w=960&h=601)

## Main Page
![resources](https://docs.google.com/drawings/d/e/2PACX-1vT9XG23bjdQ4gsNS6Wiplpr2HcgDBg-LMm9pR6noQnxWHamEa8UZM-tdC0s3rnS9G13JP-Gnk6ZA6bu/pub?w=960&h=720)

## Pokemon Detailed Page
![resources](https://docs.google.com/drawings/d/e/2PACX-1vQ9-SgbvJlN9Cv4uGtU6z4jzVTRqxs7O1TQZM1GyGBFfjhJ0_l4lwBcdp3vcbDK0cCyeI0QcXWrcFe2/pub?w=960&h=720)

## Pokemon Move Detailed Page
![resources](https://docs.google.com/drawings/d/e/2PACX-1vQNeQQ-6MYdEN-vraHIq6GpRCP6atbf2tvT19VIo99sgGCvFbzR1sTLeFsVNTrrDlv5sv27p9KqdAtb/pub?w=961&h=573)

## TODO
* Add detailed documentation of how the app works and present on Twitch live
    * ~~Add Diagrams~~
    * Include screenshots of code and of the running app in written documentation
    * Add live video recording to YouTube to be shared
* ~~Tweak colors/styles to make the look/feel for UI better~~
    * ~~Change Detailed Page to show Pokemon Name instead of "Poke Go Stats"~~
* ~~Import images of Pokemon, Moves, and Weather conditions as webp files from [here](https://pokemon.gameinfo.io/)~~
    * ~~Weather Images~~
* ~~Add Error Page for if API goes down~~
* ~~Implement Search/Sort feature for Pokemon and Moves~~
    * ~~Sort by Max CP+Type, Pokemon Types, Move Types~~
    * ~~Sort by Max CP, Pokemon Name, Move Name, Move Power~~
    * ~~Searchable Pokemon Name, Pokemon Types~~
    * ~~Searchable Move Name, Move Types~~
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
* Will likely need to get this data from a different API as Rapid API does not have this information
    * Best Attack and Defense Move Combos for each Pokemon
    * Add Pokemon Evolutions
* Add Database migration when updates roll around for app
* ~~Add Timed Death on Database via column in database for data~~
