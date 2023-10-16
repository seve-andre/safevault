package com.mitch.safevault.core.util.validator

interface Validator<in T, out R> {
    fun validate(toValidate: T): R
}

interface ValidationMatcher<in T> {
    fun matches(toMatch: T): Boolean

    fun notMatches(toMatch: T): Boolean {
        return matches(toMatch).not()
    }
}
