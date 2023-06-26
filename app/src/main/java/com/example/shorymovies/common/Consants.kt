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
     * Considering that this url would be the part of video detail response
     *
     * Which is not there at the moment
     */
    const val VIDEO_URL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"


    /**
     * Time interval for showing an poup to user after random movie selection
     */
    const val suggestionTimeInterval: Long = 500

    const val allowMovieSuggestion = true

}