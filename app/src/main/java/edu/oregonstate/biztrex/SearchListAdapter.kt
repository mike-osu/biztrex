package edu.oregonstate.biztrex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for UI binding business search results for list display
 */
internal class SearchListAdapter(private val itemsList : List<String>,
                                 private val businessListener: (String) -> Unit)
    : RecyclerView.Adapter<SearchListAdapter.SearchItemViewHolder>() {

    internal class SearchItemViewHolder(view: View, businessListener: (String) -> Unit) : RecyclerView.ViewHolder(view) {
        var itemTextView: TextView = view.findViewById(R.id.tvSearchItem)
        var layoutSearchItem: LinearLayout = view.findViewById(R.id.layoutSearchItem)

        init {
            /** navigate to expense entry screen when user clicks on a business search result */
            layoutSearchItem.setOnClickListener { businessListener(itemTextView.text.toString()) }
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

        /** different color for alternating list rows */
        holder.itemView.background =  if(position % 2 == 0)
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.ripple_alt)
        else
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.ripple)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}