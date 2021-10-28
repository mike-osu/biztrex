package edu.oregonstate.biztrex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

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
                view.findViewById<TextView>(R.id.tvAmount).text = value?.amount.toString()
                view.findViewById<TextView>(R.id.tvDatePaid).text = value?.datePaid
                val textViewId = view.findViewById<TextView>(R.id.tvId)
                textViewId.text = value?.id.toString()

                view.findViewById<LinearLayout>(R.id.layoutExpense).setOnClickListener{
                    val id: Long = (textViewId?.text ?: -1 ).toString().toLong()
                    expenseListener(id)
                }
            }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseListAdapter.ExpenseItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.expense_item, parent, false)
        return ExpenseListAdapter.ExpenseItemViewHolder(itemView, expenseListener)
    }

    override fun onBindViewHolder(holder: ExpenseItemViewHolder, position: Int) {
        holder.expense =  expensesList[position]
    }

    override fun getItemCount(): Int {
        return expensesList.count()
    }

}