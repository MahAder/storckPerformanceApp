package com.ader.stockperformanceapp.di.module

import com.ader.stockperformanceapp.presentation.ui.MainActivity
import dagger.Module

import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity
}