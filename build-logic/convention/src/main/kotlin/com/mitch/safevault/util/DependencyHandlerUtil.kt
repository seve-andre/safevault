package com.mitch.safevault.util

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dependency: Any): Dependency? =
    add("implementation", dependency)

fun DependencyHandler.testImplementation(dependency: Any): Dependency? =
    add("testImplementation", dependency)

fun DependencyHandler.testRuntimeOnly(dependency: Any): Dependency? =
    add("testRuntimeOnly", dependency)

fun DependencyHandler.androidTestImplementation(dependency: Any): Dependency? =
    add("androidTestImplementation", dependency)

fun DependencyHandler.debugImplementation(dependency: Any): Dependency? =
    add("debugImplementation", dependency)

fun DependencyHandler.ksp(dependency: Any): Dependency? = add("ksp", dependency)

fun DependencyHandler.kapt(dependency: Any): Dependency? = add("kapt", dependency)

fun DependencyHandler.kaptAndroidTest(dependency: Any): Dependency? =
    add("kaptAndroidTest", dependency)

fun DependencyHandler.kaptTest(dependency: Any): Dependency? = add("kaptTest", dependency)

fun DependencyHandler.coreLibraryDesugaring(dependency: Any): Dependency? =
    add("coreLibraryDesugaring", dependency)
