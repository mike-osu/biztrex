package edu.oregonstate.biztrex

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import edu.oregonstate.biztrex.databinding.ActivityEnterBinding
import java.text.SimpleDateFormat
import java.util.*

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

        val businessName = intent.extras?.getString("name") ?: ""
        binding.editTextBusiness.setText(businessName)

        binding.btnEnter.setOnClickListener {
            putExpense().let {
                if (it) showAlert()
            }
        }
    }

    private fun putExpense(): Boolean {
        val desc = binding.editTextBusiness.text.toString().trim()
        if (desc.isEmpty()) return false

        var amt = binding.editTextAmount.text.toString().trim()
        if (amt.isEmpty()) amt = "0.00"

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val currentDate = sdf.format(Date())

        val expense = Expense(description = desc,
            amount = amt.toFloat(),
            datePaid = currentDate,
            isArchived = false)

        val expenseBox = ObjectBox.store.boxFor(Expense::class.java)
        expenseBox.put(expense)
        return true
    }

    private fun showAlert() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Your expense has been entered.")
            .setPositiveButton("OK") { _, _ -> returnToSearch() }
        val alert = dialogBuilder.create()
        alert.setTitle("Success!")
        alert.show()
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