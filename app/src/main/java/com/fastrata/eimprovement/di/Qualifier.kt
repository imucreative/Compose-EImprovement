package com.fastrata.eimprovement.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AppAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScopeIO
