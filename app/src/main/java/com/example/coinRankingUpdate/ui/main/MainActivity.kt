package com.example.coinRankingUpdate.ui.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.coinRankingUpdate.R
import com.example.coinRankingUpdate.databinding.ActivityMainBinding
import com.example.coinRankingUpdate.ui.gone
import com.example.coinRankingUpdate.ui.visible
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var btnSwitchTheme: SwitchMaterial
    private lateinit var navController: NavController

    lateinit var configuration :AppBarConfiguration

    private val topLevelMenuItems = setOf(
        R.id.cryptocurrencyListFragment,
        R.id.searchFragment,
        R.id.bookmarkFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setDefaultLanguage()
        setDarkModeAction()
        setupNavController()
        setupNavigationUiState()
        setupToolbar()
    }

    private fun setDarkModeAction() {
        btnSwitchTheme =
            binding.navigationView.menu.findItem(R.id.item_nightMode).actionView as SwitchMaterial

        btnSwitchTheme.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    private fun setupNavController() {
        //1- get navController from navHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        //2- setUp bottom navigation view with nav controller
        binding.bottomNavigationView.setupWithNavController(navController)

    }

    private fun setupNavigationUiState() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            with(binding) {
                when (destination.id) {
                    // Hide UI controllers
                    R.id.cryptocurrencyDetailFragment -> {
                        bottomNavigationView.gone()
                        toolbar.visible()
                    }
                    R.id.splashFragment -> {
                        bottomNavigationView.gone()
                        toolbar.gone()
                    }
                    else -> {
                        bottomNavigationView.visible()
                        toolbar.visible()
                    }
                }
            }
        }
    }

    //TODO: fix toolbar
    private fun setupToolbar() {

        configuration = AppBarConfiguration.Builder(topLevelMenuItems)
            .setOpenableLayout(binding.drawerLayout)
            .build()
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, configuration)
    }

    // NavigateUp action an appbar
    override fun onSupportNavigateUp(): Boolean { // setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, configuration)
    }

    // If drawer layout was opened close it by back pressed, if not pop back stack
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setDefaultLanguage() {
        val languageToLoad = "en" // your language

        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }
}
