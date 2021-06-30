package com.thyme.eatandrun

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.thyme.eatandrun.auth.AuthActivity
import com.thyme.eatandrun.overview.OverviewFragment
import com.thyme.todolist.R
import com.thyme.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), OverviewFragment.OnOverviewCurrent {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mNavController: NavController
    private lateinit var mAppBarConfiguration: AppBarConfiguration

    override fun onOverviewCurrent(isCurrent: Boolean) {
        isOverviewCurrent = isCurrent
        //update menu
        invalidateOptionsMenu()
        if (isCurrent) {
            // set current day string
            val dbFormattedDate = selectedDate ?: getCurrentDayString()

        } else {
            supportActionBar?.subtitle = ""
        }
    }

    private var mAuth: FirebaseAuth? = null
    private lateinit var navController: NavController
    private var isOverviewCurrent = false

    private var _selectedDate: String? = null
    var selectedDate: String? = null
        get() = _selectedDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationView()
//        val binding = DataBindingUtil.setContentView<com.thyme.todolist.databinding.ActivityMainBinding>(this, R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()


        //Initialize the bottom navigation view
        //create bottom navigation view object
        val bottomNavigationView = findViewById<BottomNavigationView
                >(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(
            navController
        )



        val incomingIntent = intent
        _selectedDate = incomingIntent.getStringExtra("date")



    }

    override fun onStart() {
        super.onStart()

        var user = mAuth!!.currentUser
        if (user == null) {
            intentToAuthActivity()
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp()
//    }



    private fun intentToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }





    private fun setupNavigationView() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        mNavController = navHostFragment.navController
        mAppBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.action_overviewFragment_to_searchFragment,
            )
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp(mAppBarConfiguration) || super.onSupportNavigateUp()
    }

}
