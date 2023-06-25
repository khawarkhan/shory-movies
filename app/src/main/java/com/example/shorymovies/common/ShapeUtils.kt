package com.example.shorymovies.common

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import java.util.Random

/**
 * created by Khawar Habib on 25/06/2023
 *
 * Common utils we may/no need at different points
 *
 **/
object ShapeUtils {

    /**
     * Creates Dynamic shape drawable
     */
    fun drawRectangle(cornerRadii: FloatArray?, color: Int): Drawable {
        val gd = GradientDrawable()
        gd.setColor(color)
        gd.shape = GradientDrawable.RECTANGLE
        gd.cornerRadii = cornerRadii
        return gd
    }


    /**
     * Gets random color and returns Drawable dynammically
     */
    fun getRandomShape(): Drawable {
        val rnd = Random()
        val color = Color.argb(200, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

        val radius = 40f
        val cornerRadii =
            floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)

        return drawRectangle(cornerRadii, color)
    }
}