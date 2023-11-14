package com.pvsb.personalcostsvalidator.main

import com.pvsb.personalcostsvalidator.entity.Expense
import java.util.Date

data class MainState(
    val expenses: List<Expense> = listOf(),
    val totalSum: Double = 0.0
)