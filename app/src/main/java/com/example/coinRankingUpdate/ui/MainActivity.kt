package com.example.coinRankingUpdate.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.coinRankingUpdate.R
import com.example.coinRankingUpdate.databinding.ActivityMainBinding
import com.example.coinRankingUpdate.ui.cryptocurrency.CryptocurrencyListFragmentDirections
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var btnSwitchTheme: SwitchMaterial
    private lateinit var navController: NavController

    private val topLevelMenuItems = setOf(
        R.id.cryptocurrencyListFragment
        //TODO: add bookmark page and search page to bottom navigation
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setDarkModeAction()
        setupNavController()
        setupToolbar()
        setupDrawerLayout()
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

    private fun setupDrawerLayout() {
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.label_action_open_drawer,
            R.string.label_action_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun setupNavController() {
        //1- get navController from navHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        //2- setUp bottom navigation view with nav controller
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_search -> {
                findNavController(R.id.navHostFragment).navigate(CryptocurrencyListFragmentDirections.actionCryptocurrencyListFragmentToSearchFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //TODO: fix toolbar
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        val configuration = AppBarConfiguration.Builder(topLevelMenuItems)
            .setOpenableLayout(binding.drawerLayout)
            .build()
        setupActionBarWithNavController(navController, configuration)
    }

    // NavigateUp action an appbar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    // Sync navigation hamburger button
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    // Set new configuration to drawer layout
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    // If drawer layout was opened close it by back pressed, if not pop back stack
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
