package com.ader.stockperformanceapp.di.module

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ader.stockperformanceapp.domain.interactor.IStockPerformanceInteractor
import com.ader.stockperformanceapp.presentation.ui.MainActivity
import com.ader.stockperformanceapp.presentation.viewmodel.MainActivityViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton


@Module
class MainActivityModule {
    @Provides
    fun provideViewModelFactory(
        stockInteractor: IStockPerformanceInteractor
    ):ViewModelProvider.Factory   = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(stockInteractor) as T
        }
    }

    @Provides
    fun provideMainViewModel(activty: MainActivity, vmFactory: ViewModelProvider.Factory): MainActivityViewModel{
        return ViewModelProviders.of(activty, vmFactory)[MainActivityViewModel::class.java]
    }
}