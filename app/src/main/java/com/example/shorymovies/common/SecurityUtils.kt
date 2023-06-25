package com.example.shorymovies.common

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * created by Khawar Habib on 25/06/2023
 *
 * Will be used for app's keys security purposes
 *
 **/
object SecurityUtils {

    /**
     * returns md5 converted string
     */
    fun MD5(md5: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            val array = md.digest(md5.toByteArray(charset("UTF-8")))
            val sb = StringBuffer()
            for (i in array.indices) {
                sb.append(Integer.toHexString(array[i].toInt() and 0xFF or 0x100).substring(1, 3))
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
        } catch (ex: UnsupportedEncodingException) {
        }

        return ""
    }
}