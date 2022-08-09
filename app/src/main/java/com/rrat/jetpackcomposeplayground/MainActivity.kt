package com.rrat.jetpackcomposeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rrat.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {
                MainActivityCompose()
            }
        }
    }
}


@Composable
fun MainActivityCompose()
{
    var shouldShowOnboarding by rememberSaveable{
        mutableStateOf(true)
    }

    if(shouldShowOnboarding)
    {
        OnboardingScreen(){
            shouldShowOnboarding = false
        }

    }else{
        ModuleScreen()
    }

}

@Composable()
fun ModuleScreen()
{
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        ColumnModules()
    }

}

@Composable
fun OnboardingScreen(onContinueClicked: ()->Unit)
{

    Surface {
        Column(
            modifier= Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to the Basic App!")
            Button(
                modifier = Modifier.padding(24.dp),
                onClick = onContinueClicked) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingScreenPreview()
{
    JetpackComposePlaygroundTheme{
        OnboardingScreen(){}
    }
}
@Preview(showBackground = true)
@Composable
fun MainActivityComposePreview()
{
    JetpackComposePlaygroundTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ColumnModules()
        }
    }
}

data class Module(val name: String, val description: String)

@Composable
private fun ColumnModules() {
    val modules = List(25){Module("Module $it", "Description $it")}
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        ) {
            items(items = modules){
                module ->
                MyModule(module.name, module.description)
            }
        }

}

@Preview(showBackground = true)
@Composable
private fun MyModule(module: String = "Module 1", description: String="Description 1") {

    val expanded = rememberSaveable {mutableStateOf(false)}
/*
    val extraPadding by animateDpAsState(
        targetValue = if(expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
        )
    )
*/

    Card(
        modifier = Modifier
            //.fillMaxWidth()
            .padding(vertical = 4.dp),
        backgroundColor = MaterialTheme.colors.primary) {

        Row(
            modifier = Modifier
                .padding(24.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(12.dp)
            ) {
                Text(
                    module,
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    description,
                )
                if(expanded.value)
                {
                    Text(
                        text=("This is an example text to show how it looks. ").repeat(4),
                    )
                }
            }

            IconButton(
                onClick = { expanded.value = !expanded.value }) {
                Icon(
                    imageVector = if(expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded.value) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )

            }

        }
    }


}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun ColumnTextPreview(
    names: List<String> = listOf("Jhon", "Bob", "Angel")
) {
    Column(modifier=Modifier.padding(24.dp)) {
        for(name in names)
        {
            Text(text = "Hello $name")
        }
    }

}