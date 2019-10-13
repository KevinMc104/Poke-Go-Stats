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

# How to use the app
When the app first loads it'll take a couple seconds to load all the data. Behind the scenes, the app is calling out to several API endpoints from the [RapidAPI](https://rapidapi.com/brianiswu/api/pokemon-go1)'s Pokemon Go Endpoints. It is then taking that data, organizing it within the repository and then inserting it into the Database. The Live Data objects observe the different tables in the database and when they are populated the given fragments and activities populate their lists(RecyclerViews and lists) using the Live Data Objects. This data is stored for 24 hours and will be all be wiped once 24 hours has passed. The reasoning for this is because the API sometimes adds more Pokemon or move data to it's servers and this will keep it up to date with the latest data changes.

In Simpler terms, it gathers all the data from a server and stores it on your phone for 24 hours. When that 24 hours has passed, it will wipe all the data and regather it so that the user(you) can swipe between the different pages and click on different Pokemon/move data to get more detailed information.

## Main Page
## Pokemon Tab
On the main page of the app, you can swipe up or down to view the list of every Pokemon that exists in Pokemon Go and see its name and form, Max CP, and its types. You can tap the first three vertical dots in the upper right corner to sort by various different options
![resources](https://docs.google.com/drawings/d/e/2PACX-1vTKVL9KcYktzAl-EmwNoGpcMUJhMTiL3TKR8QeVpv3tUP_Mm2Aoyw9cI0WoNSkPd7tWfUXg3JS7endq/pub?w=962&h=547)
Or you can tap the second three vertical dots in the upper right to filter by different types.
![resources](https://docs.google.com/drawings/d/e/2PACX-1vQ1J-FTQEswhxIP2NBdadw_nhNJbKm_q-Vo2Q79lyD_SGlHtvtSWavaRbngyJEz4xqd2ucu13T0LwQj/pub?w=962&h=547)
You can also swipe right or left between the Pokemon and Moves Tabs to view either the Pokemon list page or the Moves list page

## Detailed Pokemon Page
You get to this page by tapping on any of the Pokemon rows on the Pokemon tab. On this page you can swipe left or right to view the different stats or the weather boosts for the given Pokemon. You can also swipe up or down on the stats tab to view all the different stats for that Pokemon
![resources](https://docs.google.com/drawings/d/e/2PACX-1vSKCtEbOgxefOED2ph3LUd33IZ84Civft4h0Pr3pa9-86SrIC7O3pTCu_NUd66oBQHKA5Mqv3J7fnjY/pub?w=962&h=547)

## Move Tab
The Move Tab is very similar to the Pokemon tab. You can see a list of all moves in Pokemon Go sorted by name, power, and type and swipe up or down to view the list. You can tap the first three vertical dots in the upper right corner to sort by various different options
![resources](https://docs.google.com/drawings/d/e/2PACX-1vTlt-osqn-CoABpJ8DQswO7-noQX5Y_J3RfdQ8iw7cN9TOlQ3p6jqlYCUUkh8E9eyXzt0VUVMNWZCqC/pub?w=962&h=547)
Or you can tap the second three vertical dots in the upper right to filter by different types.
![resources](https://docs.google.com/drawings/d/e/2PACX-1vThzZQjvm1GYMMgNcMY1DfPENFqgdLxLqdTIYYPoigvACG_TXlTChajLuPY49Yi4WeUA6tyFFplLL4I/pub?w=962&h=547)

## Detailed Move Page
You get to this page by tapping on any of the move rows on the move tab. On this page you can swipe up or down on the stats tab to view all the different stats for that move
![resources](https://docs.google.com/drawings/d/e/2PACX-1vRumRQOcrOY5HK9ngbi1GdO1dRj4qaBjzUUSGs1dYf-qvrUpbQXpucAqJlXlbFntNm-UsdCF8CdOAQL/pub?w=962&h=547)

# How it works
* Pseudo JSON Data of all the endpoints and Pseudo Data Models can be found [here](https://pastebin.com/UXTUaGDX)
* Pseudo SQL Test Code can be found [here](https://pastebin.com/j9iQZfRN)
* A Pseudo design layout thought up on Figma can be found [here](https://www.figma.com/file/YqNCemCw1D11ra9U2rWBj0/Poke-Go-Stats?node-id=0%3A1)
* Pokemon image file names can be inserted into the project and must follow the following format:
    * **``pokemon_{pokemonID}_{pokemonName}_{pokemonForm}``**
    * No special characters allowed such as / ? or !
    * All characters must be lower case
    * {pokemonId} & {pokemonName} are required
    * {pokemonForm} is optional(leave blank is normal form)
    * Ie. "pokemon_1_bulbasaur" or "pokemon_1_rattata_alola"

# Architectural diagrams
## Overall Design
![resources](https://docs.google.com/drawings/d/e/2PACX-1vSEKw264XJhoInCJHUuij7iSSmJ8tyjFzySChDGJnPGOkDQ_-h8Y2UwEptEt68GOYCeucWDJbLmNrgS/pub?w=960&h=720)
The Error Activity lives on its own. If server errors happen during Retrofit calls(Pokemon Go API Service) to the Rapid Pokemon Go API Servers, such as 404, 500, etc. It'll throw an error that is then caught in the error activity and displayed to the user. The app will tell the user the error number with a message saying that there was a problem with data retrieval. There is a button as seen from the screenshot below, that allows them to retry the server calls and try to downloaded the data again through essentially restarting the app through the main activity.
![resources](https://docs.google.com/drawings/d/e/2PACX-1vSYXjG9tXewWYgjbmJxe3zydf3pknijXnerTd6eBZTXPgPeKytipa6kEV8kkBtFQydCOrs30mxIL8NQ/pub?w=962&h=547)

## Database Tables
![resources](https://docs.google.com/drawings/d/e/2PACX-1vRTbpIDnDpIvrZEj814Dh2E94qJx4oKh_xq_Q_68fcrSgaQR0t0kNgbMYJteFKPosmkW6tlTkrNC5dx/pub?w=960&h=601)

## Main Page
![resources](https://docs.google.com/drawings/d/e/2PACX-1vT9XG23bjdQ4gsNS6Wiplpr2HcgDBg-LMm9pR6noQnxWHamEa8UZM-tdC0s3rnS9G13JP-Gnk6ZA6bu/pub?w=960&h=720)

## Pokemon Detailed Page
![resources](https://docs.google.com/drawings/d/e/2PACX-1vQ9-SgbvJlN9Cv4uGtU6z4jzVTRqxs7O1TQZM1GyGBFfjhJ0_l4lwBcdp3vcbDK0cCyeI0QcXWrcFe2/pub?w=960&h=720)

## Pokemon Move Detailed Page
![resources](https://docs.google.com/drawings/d/e/2PACX-1vQNeQQ-6MYdEN-vraHIq6GpRCP6atbf2tvT19VIo99sgGCvFbzR1sTLeFsVNTrrDlv5sv27p9KqdAtb/pub?w=961&h=573)

## Dagger 2 Injection
This app uses Dagger 2 injection for a lot of object/class building behind the scenes. The order of injection matters as certain objects depend on others being created to be injected. The order is defined in the AppComponent interface found [here](https://github.com/KevinMc104/Poke-Go-Stats/blob/master/app/src/main/java/com/example/pokegostats/injection/AppComponent.kt#L9-L17).
![resources](https://docs.google.com/drawings/d/e/2PACX-1vRuCP9N-fNcyG8zx7Wj6vjspx_hTwgUJtHL2F6Fs-pbr7G5jPqjfZrW6Xd-hmnC7_g7YC39w4pfeqYi/pub?w=959&h=444)
* API Module
    * Gson Converter
        * This is for converting the JSON from the endpoints to the model objects represented in this [example](https://github.com/KevinMc104/Poke-Go-Stats/blob/master/app/src/main/java/com/example/pokegostats/model/RapidPokemonGoStats.kt)
    * Cache
        * A simple cache for making http calls to the Rapid Pokemon Go Endpoints
    * OkHttpClient
        * Auth Interceptor is built into this. It adds the proper keys and authentications for calling the endpoints
    * RetrofitBuilder
        * Uses the OkHttpClient to build the retorift builder object
    * RapidPokemonGoApiService
        * Uses the BaseUrl and retrofit builder object to create this using the interfaces given [here](https://github.com/KevinMc104/Poke-Go-Stats/blob/master/app/src/main/java/com/example/pokegostats/service/RapidPokemonGoApiService.kt)
    * PokemonGoApiService
        * This is the service that uses the combined URL's built by the retrofit builder to call out to consume the data from the endpoints and populate them into the model objects
* App Module
    * This is to provide Application and Context objects to other objects as needed when they are being injected/created
* Room Module
    * Room Database
        * Creates the Android Room Database. Android Room is a wrapper around SQLite for Android.
    * Once the database is created, the following DAO's are created to allow interaction with their corresponding tables
        * Pokemon DAO
        * Pokemon Moves DAO
        * Date Cache DAO
    * Repository
        * Gets access to the Pokemon DAO, Pokemon Moves DAO, Date Cache DAO, and Pokemon Go Api Service to allow for data calls to Rapid's Pokemon Go Endpoints, organization/handling of data once gathered, and insertion into the database
* Module Builder
    * Binds data and allows injections into fragments and classes

#

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

This app is completely free and ad free. If you want to support me for this app, a great way to do that is by clicking the link below and donating

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://streamelements.com/tip/mechavoy)
