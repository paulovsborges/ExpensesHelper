package com.pvsb.personalcostsvalidator.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pvsb.personalcostsvalidator.R
import com.pvsb.personalcostsvalidator.ui.theme.AppStyle
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class AddExpenseBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainContent()
            }
        }
    }

    @Composable
    private fun MainContent() {

        var valueToRegister by remember {
            mutableStateOf("")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(AppStyle.AppColors.background)
                .fillMaxWidth()
                .wrapContentHeight(unbounded = false)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .height(4.dp)
                    .width(50.dp),
                colors = CardDefaults.cardColors(contentColor = Color.White)
            ) { }

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = stringResource(id = R.string.register_value_label),
                style = AppStyle.TextStyles.titleTextStyle
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                TextField(
                    value = valueToRegister,
                    onValueChange = {
                        valueToRegister = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.width(50.dp))

                Button(onClick = {
                    viewModel.registerExpense(valueToRegister.toDouble())
                    dismiss()
                }) {
                    Text(
                        text = stringResource(id = R.string.widget_button_label_register)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    @Composable
    @Preview
    private fun MainContentPreview() {
        MainContent()
    }
}