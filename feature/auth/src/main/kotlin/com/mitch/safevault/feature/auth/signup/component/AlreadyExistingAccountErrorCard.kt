package com.mitch.safevault.feature.auth.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.SafeVaultIcons
import com.mitch.safevault.core.designsystem.theme.extendedColorScheme
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.ui.extensions.m3.contentPadding
import com.mitch.safevault.feature.auth.R
import com.mitch.safevault.core.util.R as utilR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AlreadyExistingAccountErrorCard(
    onNavigateToLogIn: () -> Unit
) {
    Card(
        onClick = onNavigateToLogIn,
        modifier = Modifier.size(width = 280.dp, height = 100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.extendedColorScheme.warning,
            contentColor = MaterialTheme.extendedColorScheme.onWarning
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(CardDefaults.contentPadding),
            horizontalArrangement = Arrangement.spacedBy(padding.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = SafeVaultIcons.Warning, contentDescription = null)
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.already_existing_account))
                    append(". ")
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(stringResource(id = utilR.string.log_in))
                    }
                    append("!")
                },
                maxLines = 2
            )
        }
    }
}

@Preview
@Composable
private fun AlreadyExistingAccountErrorCardPreview() {
    AlreadyExistingAccountErrorCard(onNavigateToLogIn = { })
}
