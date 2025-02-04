package pl.kormateusz.weather.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.kormateusz.weather.R
import pl.kormateusz.weather.domain.models.Location

@Composable
fun LocationListItem(
    location: Location,
    onItemClick: (Location) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        onClick = { onItemClick(location) },
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(54.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                painter = painterResource(R.drawable.city),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = location.name,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
@Preview
private fun LocationListItemPreview() {
    LocationListItem(
        location = Location("Warsaw", "Key"),
        onItemClick = {},
    )
}