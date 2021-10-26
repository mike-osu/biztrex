package edu.oregonstate.biztrex

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.oregonstate.biztrex.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val itemsList = ArrayList<String>()
    private lateinit var searchListAdapter: SearchListAdapter

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.btnSearch.setOnClickListener {
            val imm = this.context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            loadList()
        }

        binding.textViewManual.setOnClickListener { businessSelected("") }

        return binding.root
    }

    private fun loadList() {
        val recyclerView: RecyclerView = binding.rvBusinesses
        searchListAdapter = SearchListAdapter(itemsList, { name: String -> businessSelected(name) })
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = searchListAdapter
        prepareItems()
    }

    private fun prepareItems() {
        itemsList.clear()
        searchListAdapter.notifyDataSetChanged()

        itemsList.add("Zuni Cafe")
        itemsList.add("House of Prime Rib")
        itemsList.add("Slanted Door")
        itemsList.add("Tadich Grill")
        itemsList.add("Krispy Kreme")
        searchListAdapter.notifyDataSetChanged()
    }

    private fun businessSelected(name: String) {
        val intent = Intent(context, EnterActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }
}