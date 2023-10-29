package com.mitch.safevault.feature.auth.login.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.SafeVaultIcons
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.core.ui.extensions.m3.contentPadding
import com.mitch.safevault.feature.auth.R

@Composable
internal fun NoExistingAccountErrorCard() {
    Card(
        modifier = Modifier.size(width = 250.dp, height = 100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(CardDefaults.contentPadding),
            horizontalArrangement = Arrangement.spacedBy(padding.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = SafeVaultIcons.Error, contentDescription = null)
            Text(text = stringResource(id = R.string.no_existing_account))
        }
    }
}

@Preview
@Composable
private fun NoExistingAccountErrorCardPreview() {
    NoExistingAccountErrorCard()
}
