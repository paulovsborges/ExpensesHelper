package com.pvsb.personalcostsvalidator.repository

import com.pvsb.personalcostsvalidator.entity.Expense
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {
    suspend fun registerExpense(expense: Expense)
    suspend fun getExpenses(): Flow<List<Expense>>
    suspend fun getAll(): List<Expense>
}