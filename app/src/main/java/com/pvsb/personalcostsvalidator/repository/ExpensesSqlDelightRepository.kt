package com.pvsb.personalcostsvalidator.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.pvsb.personalcostsvalidator.ExpensesHelperDataBase
import com.pvsb.personalcostsvalidator.entity.Expense
import com.pvsb.personalcostsvalidator.mapper.ExpenseEntityMapper.toEntity
import com.pvsb.personalcostsvalidator.mapper.ExpenseEntityMapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class ExpensesSqlDelightRepository(
    private val db: ExpensesHelperDataBase,
    private val coroutineContext: CoroutineContext
) : ExpensesRepository {

    private val queries = db.expenseEntityQueries

    override suspend fun registerExpense(expense: Expense) {
        queries.insertOrReplace(expense.toEntity())
    }

    override suspend fun getExpenses(): Flow<List<Expense>> {
        return queries.getAll().asFlow().mapToList(coroutineContext).map {
            it.map { entity -> entity.toModel() }
        }
    }

    override suspend fun getAll(): List<Expense> {
        return queries.getAll().executeAsList().map { it.toModel() }
    }
}