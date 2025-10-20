package com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.R
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.Typography

@Composable
fun Header(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.logo_sacramentum),
            contentDescription = "Logo Sacramentum",
            modifier = Modifier.size(170.dp),
            contentScale = ContentScale.Fit,
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sacramentum",
                fontFamily = Typography.titleLarge.fontFamily,
                fontSize = 35.sp,
                color = DarkBrown
            )

            Text(
                text = "Santa Rita de Cássia",
                modifier = Modifier.width(350.dp),
                color = DarkBrown,
                fontFamily = Typography.titleSmall.fontFamily,
                fontSize = 18.sp
            )
        }
    }
}