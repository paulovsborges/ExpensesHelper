package com.pvsb.personalcostsvalidator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pvsb.personalcostsvalidator.repository.ExpensesRepository
import com.pvsb.personalcostsvalidator.repository.ExpensesSqlDelightRepository
import com.pvsb.personalcostsvalidator.ui.theme.PersonalCostsValidatorTheme
import com.pvsb.personalcostsvalidator.widget.ui.theme.AppStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PersonalCostsValidatorTheme {

                val state = viewModel.state.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppStyle.AppColors.background)
                    ) {
                        items(items = state.value) {

                            Column(
                                modifier = Modifier.padding(
                                    start = 10.dp,
                                    top = 10.dp,
                                    end = 10.dp
                                )
                            ) {
                                ExpenseItem(
                                    title = it.title,
                                    value = it.value,
                                    date = it.createdAt?.formatToStringDate() ?: ""
                                )
                            }
                        }
                    }
                }
            }

            viewModel.fetchExpenses()
        }
    }

    @Composable
    private fun ExpenseItem(
        modifier: Modifier = Modifier,
        title: String,
        value: Double,
        date: String
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(unbounded = true),
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppStyle.AppColors.secondary)
                    .padding(20.dp),
            ) {

                Row {

                    Column(modifier = Modifier) {
                        Text(
                            text = stringResource(id = R.string.expense_card_title_label),
                            color = AppStyle.AppColors.gray,
                            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                            fontSize = 10.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = title,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringResource(id = R.string.expense_card_value_label),
                                    color = AppStyle.AppColors.gray,
                                    fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                                    fontSize = 10.sp
                                )

                                Spacer(modifier = Modifier.height(5.dp))

                                Text(
                                    text = value.toString(),
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                                    fontSize = 14.sp
                                )
                            }

                            Text(
                                text = date,
                                color = AppStyle.AppColors.gray,
                                fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    @Preview
    private fun ExpenseItemPreview() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            ExpenseItem(title = "Uber", value = 22.80, date = "10/11/2023")
        }
    }
}