package com.pvsb.personalcostsvalidator.main

import android.content.Intent
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.personalcostsvalidator.R
import com.pvsb.personalcostsvalidator.entity.Expense
import com.pvsb.personalcostsvalidator.formatToStringDate
import com.pvsb.personalcostsvalidator.ui.theme.AppStyle
import com.pvsb.personalcostsvalidator.ui.theme.PersonalCostsValidatorTheme
import com.pvsb.personalcostsvalidator.widget.FloatingRegisterValueActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PersonalCostsValidatorTheme {
                val state = viewModel.state.collectAsState()
                viewModel.fetchExpenses()
                Content(state.value)
            }
        }
    }

    @Composable
    private fun Content(
        state: MainState
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppStyle.AppColors.background)
            ) {

                LazyColumn(
                    modifier = Modifier
                ) {
                    items(items = state.expenses) { expense ->

                        Column(
                            modifier = Modifier.padding(
                                start = 10.dp,
                                top = 10.dp,
                                end = 10.dp
                            )
                        ) {
                            ExpenseItem(
                                title = expense.title,
                                value = expense.value,
                                date = expense.createdAt?.formatToStringDate() ?: ""
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(90.dp))
                    }
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    InfoBar(
                        value = state.totalSum,
                        onAddExpenseClick = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    FloatingRegisterValueActivity::class.java
                                )
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    @Composable
    @Preview
    private fun ContentPreview() {
        Content(
            MainState(
                expenses = List(10) {
                    Expense(
                        id = it,
                        title = "Uber ${it + 1}",
                        createdAt = Date(),
                        12.80 + it
                    )
                },
                totalSum = 24.8
            )
        )
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
    private fun InfoBar(
        modifier: Modifier = Modifier,
        value: Double,
        onAddExpenseClick: () -> Unit
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(AppStyle.AppColors.secondary)
        ) {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AppStyle.AppColors.lightBlue)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "$${value}",
                    style = AppStyle.TextStyles.titleTextStyle,
                    modifier = Modifier.alpha(0.8f)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 10.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = onAddExpenseClick,
                        colors = ButtonDefaults.buttonColors(
                            AppStyle.AppColors.lightBlue
                        )
                    ) {
                        Text(text = stringResource(id = R.string.add_expense_button_label))
                    }
                }
            }
        }
    }

    @Composable
    @Preview
    private fun InfoBarPreview() {
        InfoBar(
            value = 12.8,
            onAddExpenseClick = {}
        )
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