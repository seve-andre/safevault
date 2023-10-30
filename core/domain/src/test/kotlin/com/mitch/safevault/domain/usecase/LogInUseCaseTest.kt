package com.mitch.safevault.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import com.mitch.safevault.core.domain.repository.AuthRepository
import com.mitch.safevault.core.domain.usecase.LogInResult
import com.mitch.safevault.core.domain.usecase.LogInUseCase
import com.mitch.safevault.core.util.validator.email.EmailError
import com.mitch.safevault.core.util.validator.email.Rfc5322EmailValidator
import com.mitch.safevault.core.util.validator.password.PasswordValidator
import com.mitch.safevault.domain.repository.FakeAuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class LogInUseCaseTest {
    private lateinit var authRepository: AuthRepository
    private lateinit var logInUseCase: LogInUseCase

    @Before
    fun setUp() {
        authRepository = FakeAuthRepository()

        logInUseCase = LogInUseCase(
            emailValidator = Rfc5322EmailValidator(),
            passwordValidator = PasswordValidator(),
            repository = authRepository
        )
    }

    @Test
    fun `when blank email, log in result should be error`() = runTest {
        val result = logInUseCase.validateEmail("")

        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(EmailError.Validation.EmptyField)
    }

    @Test
    fun `when blank password, log in result should be error`() = runTest {
        val result = logInUseCase.validatePassword("")

        assertThat(result).isNotNull()
    }

    @Test
    fun `when blank email and password, log in result should have both errors`() = runTest {
        val result = logInUseCase.logIn("", "") as? LogInResult.Error

        assertThat(result).isNotNull()
        assertThat(result?.emailError).isNotNull()
        assertThat(result?.passwordError).isNotNull()
    }

    @Test
    fun `when both correct, log in result should be success`() = runTest {
        val result = logInUseCase.logIn("andrea.severi.dev@gmail.com", "Abcdef21#")

        assertThat(result).isInstanceOf(LogInResult.Success::class)
    }
}
