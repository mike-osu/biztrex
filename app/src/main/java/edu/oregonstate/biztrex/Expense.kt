package edu.oregonstate.biztrex

import java.math.BigDecimal
import java.time.LocalDate

data class Expense(var id: Long?,
                   val description: String,
                   val amount: BigDecimal,
                   val dataPaid: String,
                   val isArchived: Boolean = false)