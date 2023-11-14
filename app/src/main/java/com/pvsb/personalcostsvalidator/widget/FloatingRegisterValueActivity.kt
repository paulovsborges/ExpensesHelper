@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.pvsb.personalcostsvalidator.widget

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pvsb.personalcostsvalidator.BackgroundValueRegisterWorker
import com.pvsb.personalcostsvalidator.ExpensesHelperDataBase
import com.pvsb.personalcostsvalidator.R
import com.pvsb.personalcostsvalidator.repository.ExpensesRepository
import com.pvsb.personalcostsvalidator.repository.ExpensesSqlDelightRepository
import com.pvsb.personalcostsvalidator.ui.theme.AppStyle
import com.pvsb.personalcostsvalidator.ui.theme.PersonalCostsValidatorTheme
import kotlinx.coroutines.Dispatchers

class FloatingRegisterValueActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFinishOnTouchOutside(true)

        setContent {
            PersonalCostsValidatorTheme {
                val context = LocalContext.current

                var valueToRegister by remember {
                    mutableStateOf("")
                }

                Surface(
                    modifier = Modifier
                        .height(150.dp)
                        .width(400.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Content(
                        valueToRegister = valueToRegister,
                        onValueChanged = {
                            valueToRegister = it
                        },
                        onRegisterClicked = {
                            createWorkRequest(context, valueToRegister)
                            finish()
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        valueToRegister: String,
        onValueChanged: (String) -> Unit,
        onRegisterClicked: () -> Unit
    ) {
        Box(
            modifier = modifier
                .background(AppStyle.AppColors.background)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column {

                Text(
                    text = stringResource(id = R.string.register_value_label),
                    style = AppStyle.TextStyles.titleTextStyle
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    TextField(
                        value = valueToRegister,
                        onValueChange = onValueChanged,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.width(100.dp)
                    )

                    Spacer(modifier = Modifier.width(50.dp))

                    Button(onClick = onRegisterClicked) {
                        Text(
                            text = stringResource(id = R.string.widget_button_label_register)
                        )
                    }
                }
            }
        }
    }

    private fun createWorkRequest(
        context: Context,
        valueToRegister: String
    ) {

        val inputData = Data.Builder()
            .putDouble(
                WidgetProvider.VALUE_TO_REGISTER_TAG,
                valueToRegister.toDouble()
            ).build()

        val registerWork = OneTimeWorkRequestBuilder<BackgroundValueRegisterWorker>()
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context)
            .enqueue(registerWork)
    }

    @Composable
    @Preview
    private fun PreviewContent() {
        Surface(
            modifier = Modifier
                .height(150.dp)
                .width(400.dp),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Content(Modifier, "", {}, {})
        }
    }
}