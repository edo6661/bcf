package com.example.slicingbcf.implementation.mentor.forum_diskusi

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.ui.scaffold.DiscussionScaffold
import com.example.slicingbcf.ui.shared.textfield.SearchBarCustom

@Composable
fun SearchForumDiskusiScreen(
  modifier : Modifier = Modifier,
  onNavigateDetailForumDiskusi : (String) -> Unit,
) {
  val scrollState = rememberScrollState()

  DiscussionScaffold(
    onClick = { Log.d("discussion", "discussion") }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .statusBarsPadding()
        .padding(
          horizontal = 16.dp,
        )
        .verticalScroll(scrollState),
      verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
      TopSection()
      BottomSection()
    }
  }
}

@Composable
private fun TopSection() {
  var query by remember { mutableStateOf("") }
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically,

    ) {
    SearchBarCustom(
      onSearch = {
        Log.d("search", it)
        query = it
      },
      query = query,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      color = ColorPalette.PrimaryColor700,
      textStyle = StyledText.MobileSmallMedium,
      title = "Cari Pertanyaan",
    )

    SmallFloatingActionButton(
      onClick = { Log.d("filter", "filter clicked") },
      modifier = Modifier.size(40.dp),
      containerColor = ColorPalette.PrimaryColor100
    ) {
      Image(
        painter = painterResource(id = R.drawable.filter),
        contentDescription = "Filter",
        modifier = Modifier.size(20.dp)
      )
    }
  }
}

@Composable
private fun BottomSection() {
  Column(
    modifier = Modifier
      .background(ColorPalette.PrimaryColor100, RoundedCornerShape(16.dp))
  ) {
    repeat(6) { index ->
      ListItem(
        headlineContent = {
          Text(
            text = buildAnnotatedString {
              append("Untuk program ")
              withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("Lead")
              }
              append(" Indonesia, bagaimana ${index + 1}")
            },
            style = StyledText.MobileBaseRegular,
          )
          Text(

            "Untuk program LEAD Indonesia, bagaimana ${index + 1}",
            style = StyledText.MobileBaseRegular,
          )
        },
        overlineContent = {
          Text(
            "Bakrie Center Foundation",
            color = ColorPalette.PrimaryColor700,
            style = StyledText.MobileXsBold
          )
        },
        leadingContent = {
          Box(
            Modifier
              .size(40.dp)
              .background(ColorPalette.PrimaryContainer, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "A",
              style = MaterialTheme.typography.titleMedium,
              color = ColorPalette.SysOnPrimaryContainer,
            )
          }
        },
        modifier = Modifier.padding(4.dp),
        colors = ListItemColors(
          containerColor = ColorPalette.PrimaryColor100,
          disabledHeadlineColor = ColorPalette.OnSurface,
          disabledLeadingIconColor = ColorPalette.OnSurface,
          disabledTrailingIconColor = ColorPalette.OnSurface,
          headlineColor = ColorPalette.OnSurface,
          leadingIconColor = ColorPalette.OnSurface,
          overlineColor = ColorPalette.OnSurface,
          supportingTextColor = ColorPalette.OnSurface,
          trailingIconColor = ColorPalette.OnSurface,


          )
      )
    }
  }
}
