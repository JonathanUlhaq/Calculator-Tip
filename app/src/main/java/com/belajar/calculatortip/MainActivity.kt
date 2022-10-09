package com.belajar.calculatortip

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.belajar.calculatortip.ui.theme.CalculatorTipTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CalculatorTipPreview()
                }
            }
        }
    }
}

@Composable
fun CalculateTripScene() {

    var ammounTip by remember {
        mutableStateOf("")
    }


    var ammountPercentTip by remember {
        mutableStateOf("")
    }

    var roundAmmount by remember {
        mutableStateOf(false)
    }

    val ammountPercentTipConvert = ammountPercentTip.toDoubleOrNull()?: 0.0
    val ammounts = ammounTip.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(ammounts,ammountPercentTipConvert,roundAmmount)


    Column(modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Title
        Text(text = stringResource(id = R.string.calculate_tip),
            fontSize = 24.sp)
        Spacer(modifier = Modifier.height(6.dp))

        EditTextCostOfService(value = ammounTip, valueOnChange = {ammounTip = it})
        EditTextTipAmmount(ammountPercentTip,{ammountPercentTip = it})
        SwitchRoundedValue(roundAmmount,{roundAmmount = it})
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.tip_amount, tip),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold)
    }
    
}

@Composable
fun SwitchRoundedValue(value:Boolean, onValueChange:(Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.round_up))

        Switch(checked = value,
            onCheckedChange = onValueChange,
            colors = SwitchDefaults.colors(uncheckedThumbColor = Color.Green,
                checkedThumbColor = Color.Blue,
                uncheckedTrackColor = Color.Red,
                checkedTrackColor = Color.Magenta),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )

    }
}

@Composable
fun EditTextCostOfService(value:String, valueOnChange: (String) -> Unit) {

    val focusManager = LocalFocusManager.current
    TextField(value = value , 
        onValueChange = valueOnChange ,
        label = { Text(text = stringResource(id = R.string.cost_service))},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                                          imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(
            FocusDirection.Down)})
    )
}

@Composable
fun EditTextTipAmmount(value:String, onValueChange: (String) -> Unit) {

    val focusManager = LocalFocusManager.current

    TextField(value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
        label = { Text(text = stringResource(id = R.string.tip_percent))}
    )
}

private fun calculateTip(ammount:Double,
                         tipPercent:Double,
                         roundUp:Boolean
):String {
    var calculate = tipPercent / 100 * ammount
    if(roundUp) {
        calculate = kotlin.math.ceil(calculate)
    }
    return NumberFormat.getCurrencyInstance().format(calculate)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalculatorTipPreview() {
    CalculatorTipTheme {
        CalculateTripScene()
    }
}