package com.thyme.eatandrun

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
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

    }

    private var mAuth: FirebaseAuth? = null
    private lateinit var navController: NavController
    private var isOverviewCurrent = false

    private var _selectedDate: String? = null
    val selectedDate: String?
        get() = _selectedDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationView()
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        // Initialize the Mobile Ads SDK with an AdMob App ID.
        MobileAds.initialize(this) {}

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("ABCDEF012345"))
                .build()
        )

        // Create an ad request.
        val adRequest = AdRequest.Builder().build()

        // Start loading the ad in the background.


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

        binding.adView.loadAd(adRequest)

    }

    override fun onStart() {
        super.onStart()

        val user = mAuth!!.currentUser
        if (user == null) {
            intentToAuthActivity()
        }
    }

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

    // Called when leaving the activity
    public override fun onPause() {
        binding.adView.pause()
        super.onPause()
    }

    // Called when returning to the activity
    public override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    // Called before the activity is destroyed
    public override fun onDestroy() {
        binding.adView.destroy()
        super.onDestroy()
    }
}
