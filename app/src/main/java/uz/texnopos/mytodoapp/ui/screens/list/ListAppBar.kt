package uz.texnopos.mytodoapp.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import uz.texnopos.mytodoapp.R
import uz.texnopos.mytodoapp.components.PriorityItem
import uz.texnopos.mytodoapp.data.models.Priority
import uz.texnopos.mytodoapp.ui.theme.TOP_APP_BAR_HEIGHT
import uz.texnopos.mytodoapp.ui.theme.Typography
import uz.texnopos.mytodoapp.ui.theme.topAppBarBackgroundColor
import uz.texnopos.mytodoapp.ui.theme.topAppBarContentColor
import uz.texnopos.mytodoapp.ui.viewmodels.SharedViewModel
import uz.texnopos.mytodoapp.util.SearchAppBarState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String,
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {},
                onDeleteClicked = {}
            )
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { newText ->
                    sharedViewModel.searchTextState.value = newText
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {}
            )
        }
    }
}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.list_screen_title),
                color = MaterialTheme.colors.topAppBarContentColor)
        },
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteClicked = onDeleteClicked
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit,
) {
    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteClicked = onDeleteClicked)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit,
) {
    IconButton(onClick = onSearchClicked) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_action),
            tint = MaterialTheme.colors.topAppBarContentColor,
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(id = R.string.sort_action),
            tint = MaterialTheme.colors.topAppBarContentColor)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                onClick = { expanded = false }) {
                PriorityItem(priority = Priority.LOW)
                onSortClicked(Priority.LOW)
            }
            DropdownMenuItem(
                onClick = { expanded = false }) {
                PriorityItem(priority = Priority.HIGH)
                onSortClicked(Priority.HIGH)
            }
            DropdownMenuItem(
                onClick = { expanded = false }) {
                PriorityItem(priority = Priority.NONE)
                onSortClicked(Priority.NONE)
            }
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteClicked: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(id = R.string.delete_all_action),
            tint = MaterialTheme.colors.topAppBarContentColor)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                onClick = {
                    onDeleteClicked()
                    expanded = false
                }) {
                Text(text = stringResource(id = R.string.delete_all_action),
                    style = Typography.subtitle2)
            }
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = stringResource(id = R.string.search_placeholder),
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.disabled),
                    onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.topAppBarContentColor)
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onCloseClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor)
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Composable
@Preview
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {})
}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )

}