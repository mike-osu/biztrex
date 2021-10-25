package edu.oregonstate.biztrex

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
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

        binding.btnEnter.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Your expense has been entered.")
                .setPositiveButton("OK") { _, _ -> returnToSearch() }
            val alert = dialogBuilder.create()
            alert.setTitle("Success!")
            alert.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun returnToSearch() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}