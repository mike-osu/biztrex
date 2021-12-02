package edu.oregonstate.biztrex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * Adapter for UI binding expense data for list display
 */
internal class ExpenseListAdapter(private val expensesList: List<Expense>,
                         private val expenseListener: (Long) -> Unit)
    : RecyclerView.Adapter<ExpenseListAdapter.ExpenseItemViewHolder>() {

    internal class ExpenseItemViewHolder(private val view: View,
                                         private val expenseListener: (Long) -> Unit)
        : RecyclerView.ViewHolder(view) {

        var expense: Expense? = null
            set(value) {
                field = value
                view.findViewById<TextView?>(R.id.tvDescription).text = value?.description
                view.findViewById<TextView>(R.id.tvAmount).text = String.format("%.2f", value?.amount?.toDouble())
                view.findViewById<TextView>(R.id.tvDatePaid).text = value?.datePaid
                val textViewId = view.findViewById<TextView>(R.id.tvId)
                textViewId.text = value?.id.toString()

                /** navigate to detail when user clicks on an expense */
                view.findViewById<LinearLayout>(R.id.layoutExpense).setOnClickListener{
                    val id: Long = (textViewId?.text ?: -1 ).toString().toLong()
                    expenseListener(id)
                }
            }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_item, parent, false)
        return ExpenseItemViewHolder(itemView, expenseListener)
    }

    override fun onBindViewHolder(holder: ExpenseItemViewHolder, position: Int) {
        holder.expense =  expensesList[position]

        /** different color for alternating list rows */
        holder.itemView.background =  if(position % 2 == 0)
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.ripple_alt)
        else
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.ripple)
    }

    override fun getItemCount(): Int {
        return expensesList.count()
    }
}