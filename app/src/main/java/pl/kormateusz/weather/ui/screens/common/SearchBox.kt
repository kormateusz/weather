package pl.kormateusz.weather.ui.screens.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import pl.kormateusz.weather.R

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String = "",
    isErrorVisible: Boolean = false,
    @StringRes errorText: Int? = null,
    onTextChanged: (String) -> Unit,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorLeadingIconColor = MaterialTheme.colorScheme.error,
            errorIndicatorColor = Color.Transparent,
            errorContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onTextChanged("") }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_clear),
                        contentDescription = null
                    )
                }
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        isError = isErrorVisible,
        supportingText = {
            if (errorText != null && isErrorVisible) {
                Text(
                    text = stringResource(id = errorText),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
        onValueChange = { onTextChanged(it) },
    )
}