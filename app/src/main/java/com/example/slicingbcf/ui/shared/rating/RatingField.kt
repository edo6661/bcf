package com.example.slicingbcf.ui.shared.rating

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText


@Composable
fun RatingField(
  title : String,
  description : String,
  rating : String,
  onRatingChange : (String) -> Unit,

  ) {
  Column(
    verticalArrangement = Arrangement.spacedBy(20.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        text = title,
        style = StyledText.MobileBaseSemibold,
        color = ColorPalette.PrimaryColor700,
      )
      Text(
        text = description,
        style = StyledText.MobileSmallMedium,
      )
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        repeat(4) { index ->
          ScaleRating(
            rating = (index + 1).toString(),
            modifier = Modifier.weight(1f),
            onRatingChange = onRatingChange,
            currentRating = rating
          )
        }
      }
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "Sangat Tidak\nBaik",
          style = StyledText.MobileXsMedium,
          color = ColorPalette.Monochrome300,
          textAlign = TextAlign.Center
        )
        Text(
          text = "Sangat Baik",
          style = StyledText.MobileXsMedium,
          color = ColorPalette.Monochrome300,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}

@Composable
fun ScaleRating(
  modifier : Modifier = Modifier,
  rating : String = "1",
  onRatingChange : (String) -> Unit,
  currentRating : String
) {
  val color =
    if (rating == currentRating) ColorPalette.PrimaryColor700 else ColorPalette.Monochrome500
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .border(
        width = 1.dp,
        color = color,
        shape = RoundedCornerShape(16.dp)
      )
      .aspectRatio(1f)
      .clickable {
        onRatingChange(rating)
      }
  ) {
    Text(
      text = rating,
      style = StyledText.MobileBaseMedium,
      color = color
    )
  }
}
