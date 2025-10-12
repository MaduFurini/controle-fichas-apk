package com.sacramentum.apk.view.loginPanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.R
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.Typography

@Composable
fun LoadingScreen(onTimeout: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBrownBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_sacramentum),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(top = 80.dp)
                    .width(298.dp)
                    .height(235.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Sacramentum",
                fontSize = 50.sp,
                color = DarkBrown,
                fontFamily = Typography.titleLarge.fontFamily
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Gerenciamento de Festividades",
                fontSize = 30.sp,
                color = DarkBrown,
                fontFamily = Typography.titleSmall.fontFamily
            )

            Spacer(modifier = Modifier.height(40.dp))

            CircularProgressIndicator(
                color = DarkBrown,
                strokeWidth = 6.dp,
                modifier = Modifier.size(61.dp)
            )

            Spacer(modifier = Modifier.height(150.dp))

            Text(
                text = "Desenvolvido com carinho pelos estudantes de engenharia de software - UNIFAE",
                fontSize = 16.sp,
                color = DarkBrown,
                fontFamily = Typography.titleSmall.fontFamily,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
        onTimeout()
    }
}
