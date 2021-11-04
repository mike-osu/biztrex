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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            val searchTerm = binding.editTextSearch.text.toString().trim()
            if (searchTerm == "")
                return@setOnClickListener

            binding.progressBarHolder.visibility = View.VISIBLE
            binding.progressBarLayout.visibility = View.VISIBLE
            displayBusinesses(searchTerm, "Honolulu")
        }

        binding.textViewManual.setOnClickListener { businessSelected("") }
        return binding.root
    }

    private fun displayBusinesses(searchTerm: String, location: String) {
        // make call to teammate's (Amy) business service
        // TODO: pass search term and location querystring params
        val apiInterface = ApiInterface.create().getBusinesses(searchTerm, location)
        apiInterface.enqueue( object : Callback<List<ApiResponseItem>>{
            override fun onResponse(call: Call<List<ApiResponseItem>>?, response: Response<List<ApiResponseItem>>?) {
                loadList()
                prepareItems(response?.body())
                binding.progressBarHolder.visibility = View.GONE
                binding.progressBarLayout.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<List<ApiResponseItem>>?, t: Throwable?) {
                binding.progressBarHolder.visibility = View.GONE
                binding.progressBarLayout.visibility = View.INVISIBLE
            }
        })
    }

    private fun loadList() {
        val recyclerView: RecyclerView = binding.rvBusinesses
        searchListAdapter = SearchListAdapter(itemsList, { name: String -> businessSelected(name) })
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = searchListAdapter
    }

    private fun prepareItems(businesses: List<ApiResponseItem>?) {
        itemsList.clear()
        searchListAdapter.notifyDataSetChanged()

        businesses?.forEach {
            itemsList.add(it.business)
        }
        searchListAdapter.notifyDataSetChanged()
    }

    private fun businessSelected(name: String) {
        val intent = Intent(context, EnterActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }
}