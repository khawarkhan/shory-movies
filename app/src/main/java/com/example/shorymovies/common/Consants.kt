package com.example.shorymovies.common

/**
 * created by Khawar Habib on 25/06/2023
 *
 * Constants stuff we gonna need in the app
 *
 * ALERT!!
 *
 * We are using private/public keys which should be encrypted but due to time we are keeping open
 *
 **/
object Constants {
    /**
     * Base url for ombd api's
     */
    const val BASE_URL = "https://www.omdbapi.com/"


    /**
     * Considering that this url would be the part of video detail response
     *
     * Which is not there at the moment
     */
    const val VIDEO_URL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    /**
     * This key in real world would be decrypted on the go while used
     */
    // fixme: Khawar - use encryption/decryption mechanism once done with all features
    const val API_KEY = "f96d17f7"


    /**
     * Marvel keys, ideally should be encrypted and decrypt at run time
     */
    // fixme: Khawar - use encryption/decryption mechanism once done with all features
    const val marvelPublicKey = "aa5973d2a32bc81188ea42028d752bc5"
    const val marvelPrivate = "1067f478d02e25fb6e11c8c56e671c65010273fa"

    /**
     * marvel hero base url for comic characters
     */
    val superHeroUrl = "https://gateway.marvel.com:443/v1/public/characters"

    /**
     * Time interval for showing an poup to user after random movie selection
     */
    const val suggestionTimeInterval: Long = 500

    const val allowMovieSuggestion = false

}