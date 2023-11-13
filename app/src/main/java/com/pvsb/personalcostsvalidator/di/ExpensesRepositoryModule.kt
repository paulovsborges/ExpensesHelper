package com.pvsb.personalcostsvalidator.di

import com.pvsb.personalcostsvalidator.ExpensesHelperDataBase
import com.pvsb.personalcostsvalidator.repository.ExpensesRepository
import com.pvsb.personalcostsvalidator.repository.ExpensesSqlDelightRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object ExpensesRepositoryModule {

    @Provides
    @ViewModelScoped
    fun provides(
        db: ExpensesHelperDataBase
    ): ExpensesRepository {
        return ExpensesSqlDelightRepository(db, Dispatchers.IO)
    }
}