package com.thyme.eatandrun

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.thyme.eatandrun.ui.login.auth.AuthActivity
import com.thyme.eatandrun.ui.meal.overview.OverviewFragment
import com.thyme.eatandrun.utils.getCurrentDayString
import com.thyme.todolist.R


class MainActivity : AppCompatActivity(), OverviewFragment.OnOverviewCurrent {

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
    private var isOverviewCurrent = false

    private var _selectedDate: String? = null
    var selectedDate: String? = null
        get() = _selectedDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()


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

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    /** MENU */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (isOverviewCurrent) {
            menuInflater.inflate(R.menu.bottom_nav_menu, menu)
            return true
        }
        return false
    }


    private fun intentToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


}
