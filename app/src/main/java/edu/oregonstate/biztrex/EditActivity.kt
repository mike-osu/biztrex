package edu.oregonstate.biztrex

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import edu.oregonstate.biztrex.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setTitle(R.string.edit_expense)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnSave.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Your expense has been saved.")
                .setPositiveButton("OK") { _, _ -> returnToList() }
            val alert = dialogBuilder.create()
            alert.setTitle("Done!")
            alert.show()
        }

        binding.imageViewDelete.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Are you sure.")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ -> returnToList() }
                .setNegativeButton("Cancel") { _, _ ->  }
            val alert = dialogBuilder.create()
            alert.setTitle("Delete Expense?")
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

    private fun returnToList() {
        onBackPressed()
    }
}