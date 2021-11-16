package edu.oregonstate.biztrex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.biztrex.databinding.FragmentViewAllBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * Screen for listing all entered expenses
 */
class ViewAllFragment : Fragment() {

    private lateinit var binding: FragmentViewAllBinding

    private val expensesList = ArrayList<Expense>()
    private lateinit var expenseListAdapter: ExpenseListAdapter

    companion object {
        fun newInstance(): ViewAllFragment {
            return ViewAllFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewAllBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loadList()
    }

    private fun loadList() {
        val recyclerView: RecyclerView = binding.rvExpenses
        expenseListAdapter = ExpenseListAdapter(expensesList, { id: Long -> expenseSelected(id) })
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = expenseListAdapter
        prepareItems()
    }

    /**
     * Populate locally persisted expenses in a list
     */
    private fun prepareItems() {
        expensesList.clear()
        expenseListAdapter.notifyDataSetChanged()
        expensesList.addAll(ObjectBox.store.boxFor(Expense::class.java).all)
    }

    /**
     * Listener method when user selects an expense
     * Navigates to edit expense screen
     *
     * @param id    id of selected expense
     */
    private fun expenseSelected(id: Long) {
        val intent = Intent(context, EditActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}