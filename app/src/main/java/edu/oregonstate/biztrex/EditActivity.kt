package edu.oregonstate.biztrex

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import edu.oregonstate.biztrex.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var expenseDate: String
    private var expenseId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setTitle(R.string.edit_expense)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        expenseId = intent.extras?.getLong("id")
        val expenseBox = ObjectBox.store.boxFor(Expense::class.java)
        expenseId?.let { expenseBox.get(it) }?.apply {
            binding.editTextBusinessEdit.setText(this.description)
            binding.editTextAmountEdit.setText(this.amount.toString())
            expenseDate = this.datePaid.toString()
        }

        binding.btnSave.setOnClickListener {
            saveExpense().let { if (it) showAlert() }
        }

        binding.imageViewDelete.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Are you sure.")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    deleteExpense().let {
                        if (it) returnToList()
                    }
                }
                .setNegativeButton("Cancel") { _, _ ->  }
            val alert = dialogBuilder.create()
            alert.setTitle("Delete Expense?")
            alert.show()
        }
    }

    private fun saveExpense(): Boolean {
        val desc = binding.editTextBusinessEdit.text.toString().trim()
        if (desc.isEmpty()) return false

        var amt = binding.editTextAmountEdit.text.toString().trim()
        if (amt.isEmpty()) amt = "0.00"

        val expense = Expense(expenseId!!, desc, amt.toFloat(), expenseDate)
        val expenseBox = ObjectBox.store.boxFor(Expense::class.java)
        expenseBox.put(expense)
        return true
    }

    private fun showAlert() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Your expense has been saved.")
            .setPositiveButton("OK") { _, _ -> returnToList() }
        val alert = dialogBuilder.create()
        alert.setTitle("Done!")
        alert.show()
    }

    private fun deleteExpense(): Boolean {
        return ObjectBox.store.boxFor(Expense::class.java).remove(expenseId!!)
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