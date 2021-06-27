package com.nero.bookparking

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.nero.bookparking.databinding.ActivityMainScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val hView = navigationView.getHeaderView(0)

        val navUser = hView.findViewById<TextView>(R.id.tv_user_name)

        val navUserId = hView.findViewById<TextView>(R.id.tv_user_email_id)

        val navProfile = hView.findViewById<CircleImageView>(R.id.ivProfile)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController

        navigationView.itemIconTintList = null

        NavigationUI.setupActionBarWithNavController(this, navController, drawer)
        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this@MainActivity)

        if (intent != null && intent.extras != null) {
            val userName: String = intent.getStringExtra("UserName").toString()
            navUser.text = userName

            val userEmail: String = intent.getStringExtra("UserEmail").toString()
            navUserId.text = userEmail

            val userPhoto = intent.getStringExtra("UserPhoto")
            Glide.with(navProfile).load(userPhoto).into(navProfile)
        }

        binding.apply {

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(
                this,
                R.id.fragmentContainerView2
            ), drawer
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> return if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
                true
            } else false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_home -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.locationFragment, true).build()
                Navigation.findNavController(this, R.id.fragmentContainerView2)
                    .navigate(R.id.locationFragment, null, navOptions)
            }
            R.id.nav_my_bookings -> {
                if (isValidDestination(R.id.nav_my_bookings)) {
                        Navigation.findNavController(this,R.id.fragmentContainerView2)
                            .navigate(R.id.nav_my_bookings)
                }
            }
            R.id.nav_payment -> {
                if (isValidDestination(R.id.slotBookingFragment)) {
                    Navigation.findNavController(this, R.id.fragmentContainerView2)
                        .navigate(R.id.slotBookingFragment)
                }
            }
            R.id.nav_settings -> {

            }
            R.id.nav_logout -> {

            }
        }

        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    private fun isValidDestination(destination: Int): Boolean {
        return destination != Navigation.findNavController(this, R.id.fragmentContainerView2)
            .currentDestination?.id
    }


    fun hideToolBar() {

        binding.myToolbar.visibility = View.GONE

    }

    fun showToolBar() {
        binding.myToolbar.visibility = View.VISIBLE

    }

}