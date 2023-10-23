package com.mitch.safevault.core.util.validator

interface Validator<in T, out R> {
    fun validate(toValidate: T): R
}
