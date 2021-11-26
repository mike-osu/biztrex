package edu.oregonstate.biztrex

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import edu.oregonstate.biztrex.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Screen for searching for local businesses
 */
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val itemsList = ArrayList<String>()
    private lateinit var searchListAdapter: SearchListAdapter
    private var latitude: Double? = null
    private var longitude: Double? = null

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.btnSearch.setOnClickListener {
            /** collapse keyboard if visible when user clicks 'Search' */
            val imm = this.context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            val searchTerm = binding.editTextSearch.text.toString().trim()
            if (searchTerm == "")
                return@setOnClickListener

           /** display spinning circle while waiting for API response */
            binding.progressBarHolder.visibility = View.VISIBLE
            binding.progressBarLayout.visibility = View.VISIBLE
            displayBusinesses(searchTerm)
        }

        binding.textViewManual.setOnClickListener { businessSelected("") }

        getLocation()

        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation
            .addOnSuccessListener { location: Location? ->
                latitude =  location?.latitude
                longitude = location?.longitude
            }
    }

    /**
     * Calls Amy's business service API
     *
     * @param searchTerm    user-entered search keyword(s)
     */
    private fun displayBusinesses(searchTerm: String) {
        val apiInterface = if (latitude != null && longitude != null)
            ApiInterface.create().getBusinessesByLatLong(searchTerm, latitude!!, longitude!!)
        else
            ApiInterface.create().getBusinesses(searchTerm, "San Francisco")

        apiInterface.enqueue( object : Callback<List<ApiResponseItem>>{
            override fun onResponse(call: Call<List<ApiResponseItem>>?, response: Response<List<ApiResponseItem>>?) {
                loadList()
                prepareItems(response?.body())
                binding.progressBarHolder.visibility = View.GONE
                binding.progressBarLayout.visibility = View.INVISIBLE
                if (response?.body()?.isNotEmpty() == true)
                    binding.imageSearch.visibility = View.INVISIBLE
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
        /** clear any prior results from list */
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