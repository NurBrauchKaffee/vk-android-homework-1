package ru.justneedcoffee.simplelist

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.justneedcoffee.simplelist.ui.theme.SimpleListTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleListTheme {
                val context = LocalContext.current

                MainScreen(
                    items = viewModel.items,
                    onAddItem = { viewModel.addItem() },
                    onItemClick = { number ->
                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.EXTRA_NUMBER, number)
                        }
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    items: List<Int>,
    onAddItem: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItem) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_item_desc)
                )
            }
        }
    ) { innerPadding ->
        val horizontalColumn = 4
        val verticalColumn = 3

        val configuration = LocalConfiguration.current
        val columnCount = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            horizontalColumn
        } else {
            verticalColumn
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columnCount),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                start = dimensionResource(id = R.dimen.grid_spacing),
                end = dimensionResource(id = R.dimen.grid_spacing),
                top = dimensionResource(id = R.dimen.grid_spacing),
                // отступ, чтобы не перекрывать кнопку
                bottom = dimensionResource(id = R.dimen.grid_bottom_padding)
            ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.grid_spacing)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.grid_spacing))
        ) {
            items(items) { number ->
                GridItem(
                    number = number,
                    onClick = { onItemClick(number) }
                )
            }
        }
    }
}

@Composable
fun GridItem(number: Int, onClick: () -> Unit) {
    val backgroundColor = if (number % 2 == 0) {
        colorResource(id = R.color.even_color)
    } else {
        colorResource(id = R.color.odd_color)
    }

    Box(
        modifier = Modifier
            // добавляем соотношение сторон 1:1
            .aspectRatio(1f)
            .background(backgroundColor)
            .clickable (onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$number",
            fontSize = dimensionResource(id = R.dimen.card_text_size).value.sp,
            color = colorResource(id = R.color.card_text_color),
            fontWeight = FontWeight.Bold
        )
    }
}