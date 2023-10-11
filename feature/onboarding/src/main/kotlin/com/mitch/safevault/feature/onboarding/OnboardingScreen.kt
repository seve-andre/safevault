package com.mitch.safevault.feature.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.theme.padding
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowForward
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun OnboardingRoute(
    onSignupClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val items = listOf(
        CarouselItem(
            image = painterResource(R.drawable.shield),
            title = "Secure your items",
            body = "Ensure your safety by securely storing your logins, passwords, and more within SafeVault"
        ),
        CarouselItem(
            image = painterResource(R.drawable.lock),
            title = "Lock ‘em",
            body = "Safeguard your sensitive data with a master password, your key to locking and securing everything inside the vault"
        ),
        CarouselItem(
            image = painterResource(R.drawable.key),
            title = "Use your key wisely",
            body = "Relax, you're the one with the key to your stuff – no one else gets in!"
        )
    )
    val pagerState = rememberPagerState(pageCount = { items.size })

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.weight(7f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fill
            ) { index ->
                val currentItem = items[index]
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1) image + title + body text
                    Image(
                        painter = currentItem.image,
                        contentDescription = null,
                        modifier = Modifier.size(300.dp)
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(
                        text = currentItem.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = currentItem.body,
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center,
                        minLines = 3
                    )
                    Spacer(modifier = Modifier.height(50.dp))


                    // 2) indicators
                    CarouselDotIndicators(
                        pageCountProvider = { pagerState.pageCount },
                        currentPageProvider = { pagerState.currentPage }
                    )
                }
            }
        }

        val scope = rememberCoroutineScope()

        // 3) controls (skip/next or login/register)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage + 1 != items.size) {
                TextButton(
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(items.size - 1)
                        }
                    }
                ) {
                    Text(text = "Skip")
                }

                Button(
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                ) {
                    Text(text = "Next")
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(
                        imageVector = EvaIcons.Outline.ArrowForward,
                        contentDescription = "Go to next",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            } else {
                OutlinedButton(onClick = onLoginClick) {
                    Text(text = "Login")
                }

                Button(onClick = onSignupClick) {
                    Text(text = "Sign up")
                }
            }
        }


    }
}

@Composable
private fun CarouselDotIndicators(
    pageCountProvider: () -> Int,
    currentPageProvider: () -> Int
) {
    Row(horizontalArrangement = Arrangement.spacedBy(padding.small)) {
        repeat(pageCountProvider()) {
            val backgroundColor =
                if (currentPageProvider() == it) MaterialTheme.colorScheme.primary else Color.Transparent
            val borderColor =
                if (currentPageProvider() == it) Color.Transparent else MaterialTheme.colorScheme.onBackground

            Box(
                modifier = Modifier
                    .size(15.dp)
                    .background(
                        color = backgroundColor,
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = CircleShape
                    )
            )
        }
    }
}

data class CarouselItem(
    val image: Painter,
    val title: String,
    val body: String
)

@Preview
@Composable
private fun OnboardingPreview() {
    OnboardingRoute(
        onSignupClick = { },
        onLoginClick = { }
    )
}
