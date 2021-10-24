package edu.oregonstate.biztrex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

internal class SearchListAdapter(private val itemsList : List<String>,
                                 private val businessListener: (String) -> Unit)
    : RecyclerView.Adapter<SearchListAdapter.SearchItemViewHolder>() {

    internal class SearchItemViewHolder(view: View, businessListener: (String) -> Unit) : RecyclerView.ViewHolder(view) {
        var itemTextView: TextView = view.findViewById(R.id.tvSearchItem)

        init {
            itemTextView.setOnClickListener { businessListener(itemTextView.text.toString()) }
        }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item, parent, false)
        return SearchItemViewHolder(itemView, businessListener)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val item = itemsList[position]
        holder.itemTextView.text = item
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}