package com.pvsb.personalcostsvalidator.entity

import java.util.Date

data class Expense(
    val id: Int,
    val title: String,
    val createdAt: Date?,
    val value : Double
)
