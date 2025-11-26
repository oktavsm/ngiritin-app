package com.ngiritin.app.ui.navbar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ngiritin.app.R
import com.ngiritin.app.ui.history.HistoryFragment
import com.ngiritin.app.ui.home.HomeFragment
import com.ngiritin.app.ui.navbar.pageDummy.AddTransactionDummyFragment

import com.ngiritin.app.ui.navbar.pageDummy.ForYouDummyFragment
import com.ngiritin.app.ui.navbar.pageDummy.ProfileDummyFragment
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import com.ngiritin.app.ui.new_transaction.AddOptionBottomSheet
import com.ngiritin.app.ui.new_transaction.AutomaticTransactionFragment
import com.ngiritin.app.ui.new_transaction.ManualTransactionFragment

class BottomNavbarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bottom_navbar)

        val bottomNavigation = findViewById<CurvedBottomNavigation>(R.id.bottomNavigation)
        bottomNavigation.add(
            CurvedBottomNavigation.Model(1, "", R.drawable.ic_dashboard1)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(2, "", R.drawable.ic_history1)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(3, "", R.drawable.ic_add1)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(4, "", R.drawable.ic_foryou2)
        )
        bottomNavigation.add(
            CurvedBottomNavigation.Model(5, "", R.drawable.ic_profile1)
        )

        bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                1 -> {
                    replaceFragment(HomeFragment())
                }
                2 -> {
                    replaceFragment(HistoryFragment())
                }
                3 -> {
                    val bottomSheet = AddOptionBottomSheet { selectedOption ->
                        when (selectedOption) {
                            "automatic" -> {
                                loadTransactionFragment(AutomaticTransactionFragment())
                            }
                            "manual" -> {
                                loadTransactionFragment(ManualTransactionFragment())
                            }
                        }
                    }
                    bottomSheet.show(supportFragmentManager, "AddOptionBottomSheet")
                }
                4 -> {
                    replaceFragment(ForYouDummyFragment())
                }
                5 -> {
                    replaceFragment(ProfileDummyFragment())
                }
            }
        }

        // default
        replaceFragment(HomeFragment())
        bottomNavigation.show(1)
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun loadTransactionFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
