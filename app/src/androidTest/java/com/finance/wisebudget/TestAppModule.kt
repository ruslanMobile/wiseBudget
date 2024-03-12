package com.finance.wisebudget

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [DataModule::class]
//)
@InstallIn(SingletonComponent::class)
object TestAppModule {

}