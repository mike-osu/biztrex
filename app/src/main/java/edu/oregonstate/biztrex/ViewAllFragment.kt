package edu.oregonstate.biztrex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.biztrex.databinding.FragmentViewAllBinding
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ViewAllFragment : Fragment() {

    private lateinit var binding: FragmentViewAllBinding

    private val expensesList = ArrayList<Expense>()
    private lateinit var expenseListAdapter: ExpenseListAdapter

    companion object {
        fun newInstance(): ViewAllFragment {
            return ViewAllFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAllBinding.inflate(layoutInflater)
        loadList()
        return binding.root
    }

    private fun loadList() {
        val recyclerView: RecyclerView = binding.rvExpenses
        expenseListAdapter = ExpenseListAdapter(expensesList, { id: Long -> expenseSelected(id) })
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = expenseListAdapter
        prepareItems()
    }

    private fun prepareItems() {
        expensesList.clear()
        expenseListAdapter.notifyDataSetChanged()

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val currentDate = sdf.format(Date())

        expensesList.add(Expense(1, "Chevron", BigDecimal("52.34"), currentDate))
        expensesList.add(Expense(2, "Motel 6", BigDecimal("39.99"), currentDate))
        expensesList.add(Expense(3, "Krispy Kreme", BigDecimal("12.34"), currentDate))
    }

    private fun expenseSelected(id: Long) {
        val intent = Intent(context, EditActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}