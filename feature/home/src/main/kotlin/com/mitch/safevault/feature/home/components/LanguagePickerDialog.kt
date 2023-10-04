package com.mitch.safevault.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.mitch.safevault.core.designsystem.theme.padding
import com.mitch.safevault.feature.home.R

@Composable
fun LanguagePickerDialog(
    selectedLanguage: com.mitch.safevault.core.util.SafeVaultLanguage,
    onDismiss: () -> Unit,
    onConfirm: (com.mitch.safevault.core.util.SafeVaultLanguage) -> Unit
) {
    var tempLanguage by remember { mutableStateOf(selectedLanguage) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(painterResource(R.drawable.languages), contentDescription = null) },
        title = {
            Text(text = stringResource(R.string.change_language))
        },
        text = {
            Column(Modifier.selectableGroup()) {
                com.mitch.safevault.core.util.SafeVaultLanguage.values().forEach { language ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .selectable(
                                selected = (language == tempLanguage),
                                onClick = { tempLanguage = language },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = padding.medium),
                        horizontalArrangement = Arrangement.spacedBy(padding.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (language == tempLanguage),
                            onClick = null
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(padding.small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(language.flagId),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .testTag(language.flagId.toString())
                            )
                            Text(
                                text = language.locale.displayLanguage,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(tempLanguage)
                    onDismiss()
                },
                enabled = tempLanguage != selectedLanguage
            ) {
                Text(stringResource(R.string.save))
            }
        }
    )
}
