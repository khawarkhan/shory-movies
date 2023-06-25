package com.example.shorymovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.shorymovies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity,
 *
 * This is the anchor point of any application (could be renamed)
 *
 * Handles flow of different screens (fragment)
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // use custom toolbar
        setSupportActionBar(binding.toolbar)

        /** prepare jetpack navigation component to hand fragment's flow (Home,Movies and Detail screens) */
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.findNavController().run {
            binding.toolbar.setupWithNavController(this, AppBarConfiguration(graph))
        }

        /** handles screen title, show the title of whichever screen is showing currently */
        NavController.OnDestinationChangedListener { _, destination, _ ->


            binding.toolbar.title = when (destination.id) {
                R.id.charactersFragment -> getString(R.string.app_name)
                else -> getString(R.string.movie_listing)
            }
        }
    }
}