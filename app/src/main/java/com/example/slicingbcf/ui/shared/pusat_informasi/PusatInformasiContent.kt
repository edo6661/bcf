package com.example.slicingbcf.ui.shared.pusat_informasi

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.slicingbcf.R
import com.example.slicingbcf.constant.ColorPalette
import com.example.slicingbcf.constant.StyledText
import com.example.slicingbcf.data.local.DataPusatInformasi
import com.example.slicingbcf.ui.shared.textfield.OutlineTextFieldComment
import com.example.slicingbcf.util.formatDate


@Composable
fun PusatInformasiContent(
  data : DataPusatInformasi,
  isCommentable : Boolean = true,
  isEnabledTextField : Boolean = false,
  question : String = "",
  onChangeQuestion : (String) -> Unit = {},
  onSubmitComment : (DataPusatInformasi) -> Unit = {},
  onCancelComment : () -> Unit = {},
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        painter = painterResource(id = data.profilePicture ?: R.drawable.ic_launcher_background),
        contentDescription = "Profile Picture",
        modifier = Modifier
          .size(40.dp)
          .clip(RoundedCornerShape(50)),
        contentScale = ContentScale.Crop,
      )
      Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = "Guest",
          style = StyledText.MobileBaseMedium,
          color = ColorPalette.OnSurface
        )
        Text(
          text = formatDate(data.timestamp),
          style = StyledText.MobileXsRegular,
          color = ColorPalette.Monochrome400
        )
      }
      IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier.size(64.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.icon_heart),
          contentDescription = "More",
          modifier = Modifier.size(24.dp),
        )
      }

    }
    Text(
      text = data.question,
      style = StyledText.MobileBaseRegular,
      color = ColorPalette.OnSurfaceVariant
    )
    if (isCommentable) {

      OutlineTextFieldComment(
        value = question,
        onValueChange = {
          onChangeQuestion(it)
        },
        onSubmit = {
          onSubmitComment(
            DataPusatInformasi(
              profilePicture = R.drawable.ic_launcher_background,
              username = "Guest",
              question = question,
              timestamp = System.currentTimeMillis(),
            )
          )
        },
        label = "Tambah komentar",
        placeholder = "",
        isEnabled = isEnabledTextField,
      )

    }
  }
}
