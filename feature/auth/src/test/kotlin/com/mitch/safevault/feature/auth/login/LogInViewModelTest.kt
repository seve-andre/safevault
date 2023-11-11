package com.mitch.safevault.feature.auth.login

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.mitch.safevault.core.domain.usecase.LogInUseCase
import com.mitch.safevault.core.testing.util.MainDispatcherRule
import com.mitch.safevault.core.util.validator.email.Rfc5322EmailValidator
import com.mitch.safevault.core.util.validator.password.PasswordValidator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class LogInViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: LogInViewModel

    @Before
    fun setUp() {
        viewModel = LogInViewModel(
            logInUseCase = LogInUseCase(
                emailValidator = Rfc5322EmailValidator(),
                passwordValidator = PasswordValidator(),
                repository = FakeAuthRepository()
            )
        )
    }

    @Test
    fun whenFormHasValidationErrors_logInUiStateShouldStillBeIdle() {
        assertThat(viewModel.logInUiState.value).isEqualTo(LogInUiState.Idle)
        viewModel.logIn()
        assertThat(viewModel.logInUiState.value).isEqualTo(LogInUiState.Idle)
    }

    @Test
    fun whenFormDoesNotHaveErrors_logInUiStateShouldNotBeIdle() = runTest {
        val emailCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.emailState.collect { it.textFieldState.update(correctEmailGenerator()) }
        }

        val passwordCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.passwordState.collect { it.textFieldState.update(correctPasswordGenerator()) }
        }
        assertThat(viewModel.logInUiState.value).isEqualTo(LogInUiState.Idle)

        viewModel.logIn()

        assertThat(viewModel.logInUiState.value).isNotInstanceOf(LogInUiState.Idle::class)

        emailCollectJob.cancel()
        passwordCollectJob.cancel()
    }

    @Test
    fun whenLoggingIn_shouldStartValidationIfNotAlreadyStarted() = runTest {
        val emailCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.emailState.collect()
        }

        val passwordCollectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.passwordState.collect()
        }

        assertThat(viewModel.emailState.value.validationError).isNull()
        assertThat(viewModel.passwordState.value.validationError).isNull()

        viewModel.logIn()

        assertThat(viewModel.emailState.value.validationError).isNotNull()
        assertThat(viewModel.passwordState.value.validationError).isNotNull()

        emailCollectJob.cancel()
        passwordCollectJob.cancel()
    }
}
