package edu.oregonstate.biztrex

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Expense(
    @Id
    var id: Long = 0,
    var description: String? = null,
    var amount: Float = 0.0f,
    var datePaid: String? = null,
    var isArchived: Boolean = false
)