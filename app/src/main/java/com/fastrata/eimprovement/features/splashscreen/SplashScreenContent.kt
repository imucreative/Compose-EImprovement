package com.fastrata.eimprovement.features.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.ui.theme.ImprovementTheme

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .clip(shape = CircleShape)
                .padding(50.dp, 100.dp, 50.dp, 50.dp),
            painter = painterResource(id = R.drawable.logo_eimprovement_full),
            contentDescription = stringResource(R.string.app_name)
        )

        Image(
            modifier = Modifier
                .clip(shape = CircleShape)
                .padding(50.dp, 0.dp, 50.dp, 0.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.app_name)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenContentPreview() {
    ImprovementTheme {
        SplashScreenContent()
    }
}
