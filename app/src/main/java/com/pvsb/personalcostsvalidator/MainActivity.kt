package com.pvsb.personalcostsvalidator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pvsb.personalcostsvalidator.repository.ExpensesRepository
import com.pvsb.personalcostsvalidator.repository.ExpensesSqlDelightRepository
import com.pvsb.personalcostsvalidator.ui.theme.PersonalCostsValidatorTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val repository: ExpensesRepository by lazy {
        val driver = AndroidSqliteDriver(
            schema = ExpensesHelperDataBase.Schema,
            context = this,
            name = "expensehelper.db"
        )

        val database = ExpensesHelperDataBase(driver)

        ExpensesSqlDelightRepository(database, Dispatchers.IO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PersonalCostsValidatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val scope = rememberCoroutineScope()
                    var count by remember {
                        mutableStateOf(-1)
                    }

                    LaunchedEffect(key1 = scope) {
                        scope.launch {
                            count = repository.getAll().size
                        }
                    }

                    Greeting("Expenses count $count")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PersonalCostsValidatorTheme {
        Greeting("Android")
    }
}