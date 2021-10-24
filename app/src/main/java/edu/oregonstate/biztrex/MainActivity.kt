package edu.oregonstate.biztrex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.oregonstate.biztrex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val searchFragment = SearchFragment.newInstance()
    private val viewAllFragment = ViewAllFragment.newInstance()
    private val aboutFragment = AboutFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(R.layout.activity_main)
        setContentView(view)
        setUpBottomNavView()
    }

    private fun setUpBottomNavView() {
        if (supportFragmentManager.fragments.size == 0)
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, searchFragment).commit()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()

            when (item.itemId) {
                R.id.search -> transaction.replace(R.id.fragment_container, searchFragment)
                R.id.view_all -> transaction.replace(R.id.fragment_container, viewAllFragment)
                R.id.about-> transaction.replace(R.id.fragment_container, aboutFragment)
            }

            transaction.commit()

            true
        }
    }
}