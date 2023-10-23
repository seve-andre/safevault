package com.mitch.safevault.core.util.di

import com.mitch.safevault.core.util.validator.email.EmailValidator
import com.mitch.safevault.core.util.validator.email.Rfc5322EmailValidator
import com.mitch.safevault.core.util.validator.password.PasswordValidator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun providesEmailValidator(): EmailValidator {
        return Rfc5322EmailValidator()
    }

    @Provides
    fun bindsPasswordValidator(): PasswordValidator {
        return PasswordValidator()
    }
}
