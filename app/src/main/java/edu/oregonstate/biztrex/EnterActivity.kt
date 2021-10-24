package edu.oregonstate.biztrex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import edu.oregonstate.biztrex.databinding.ActivityEnterBinding

class EnterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setTitle(R.string.enter_new_expense)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        var businessName = intent.extras?.getString("name") ?: ""
        binding.editTextBusiness.setText(businessName)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}