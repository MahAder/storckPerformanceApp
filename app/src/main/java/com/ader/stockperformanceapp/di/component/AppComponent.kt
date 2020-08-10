package com.ader.stockperformanceapp.di.component

import com.ader.stockperformanceapp.StockPerformanceApplication
import com.ader.stockperformanceapp.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class, ActivityBuilderModule::class, RepositoryModule::class, InteractorModule::class])
interface AppComponent : AndroidInjector<StockPerformanceApplication> {
    override fun inject(application: StockPerformanceApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: StockPerformanceApplication): Builder

        fun build(): AppComponent
    }
}