package com.pvsb.personalcostsvalidator

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pvsb.personalcostsvalidator.entity.Expense
import com.pvsb.personalcostsvalidator.repository.ExpensesRepository
import com.pvsb.personalcostsvalidator.repository.ExpensesSqlDelightRepository
import com.pvsb.personalcostsvalidator.widget.WidgetProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class BackgroundValueRegisterWorker(
    appContext: Context,
    params: WorkerParameters
) : Worker(appContext, params) {

    private val repository: ExpensesRepository by lazy {
        val driver = AndroidSqliteDriver(
            schema = ExpensesHelperDataBase.Schema,
            context = appContext,
            name = "expensehelper.db"
        )

        val database = ExpensesHelperDataBase(driver)

        ExpensesSqlDelightRepository(database, Dispatchers.IO)
    }

    override fun doWork(): Result {

        val valueToRegister = inputData.getDouble(WidgetProvider.VALUE_TO_REGISTER_TAG, -1.0)

        if (valueToRegister < 0) return Result.failure()

        CoroutineScope(Dispatchers.IO).launch {
            repository.registerExpense(
                Expense(
                    id = repository.getAll().size,
                    title = "Uber",
                    createdAt = Date(),
                    value = valueToRegister
                )
            )
        }


        return Result.success()
    }
}