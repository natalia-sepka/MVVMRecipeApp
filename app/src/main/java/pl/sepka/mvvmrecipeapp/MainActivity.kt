package pl.sepka.mvvmrecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.sepka.mvvmrecipeapp.ui.theme.MVVMRecipeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMRecipeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Screen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun Screen() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFF2F2F2))
    ) {
        Image(
            painter = painterResource(id = R.drawable.happy_meal_small),
            contentDescription = "Happy meal image",
            modifier = Modifier.height(300.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Happy Meal",
                style = TextStyle(fontSize = 26.sp)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "800 Calories",
                style = TextStyle(fontSize = 17.sp)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "$5.99",
                style = TextStyle(
                    color = Color(0xFF85bb65),
                    fontSize = 17.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MVVMRecipeAppTheme {
        Screen()
    }
}
