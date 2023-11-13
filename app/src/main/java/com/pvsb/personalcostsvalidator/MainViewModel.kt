package com.pvsb.personalcostsvalidator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.personalcostsvalidator.entity.Expense
import com.pvsb.personalcostsvalidator.repository.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ExpensesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(listOf<Expense>())
    val state = _state.asStateFlow()

    fun fetchExpenses() {
        viewModelScope.launch {
            repository.getExpenses().onEach { list ->
                _state.update {
                    list
                }
            }.collect()
        }
    }

}