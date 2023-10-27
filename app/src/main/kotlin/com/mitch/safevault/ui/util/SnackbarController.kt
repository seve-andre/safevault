package com.mitch.safevault.ui.util

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * If a snackbar is visible and the user triggers a second snackbar to show, it will remove
 * the first one and show the second. Likewise with a third, fourth, ect...
 *
 * If a mechanism like this is not used, snackbar get added to the Scaffolds "queue", and will
 * show one after another. I don't like that.
 */
class SnackbarController(
    private val snackbarHostState: SnackbarHostState,
    val scope: CoroutineScope
) {

    private var snackbarJob: Job? = null

    private val snackbarResult = CompletableDeferred<SnackbarResult>()

    init {
        cancelActiveJob()
    }

    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ): SnackbarResult {
        if (snackbarJob == null) {
            snackbarJob = scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = duration
                )
                snackbarResult.complete(result)
                cancelActiveJob()
            }
        } else {
            cancelActiveJob()
            snackbarJob = scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                )
                cancelActiveJob()
            }
        }

        return snackbarResult.await()
    }

    private fun cancelActiveJob() {
        snackbarJob?.let { job ->
            job.cancel()
            snackbarJob = Job()
        }
    }
}
