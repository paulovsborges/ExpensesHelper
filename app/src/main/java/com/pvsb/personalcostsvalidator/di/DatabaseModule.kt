package com.pvsb.personalcostsvalidator.di

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pvsb.personalcostsvalidator.ExpensesHelperDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DatabaseModule {

    @Provides
    @ViewModelScoped
    fun provides(
        @ApplicationContext context: Context
    ): ExpensesHelperDataBase {

        val driver = AndroidSqliteDriver(
            schema = ExpensesHelperDataBase.Schema,
            context = context,
            name = "expensehelper.db"
        )

        return ExpensesHelperDataBase(driver)
    }
}