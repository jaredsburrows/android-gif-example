package com.burrowsapps.example.gif.di.component

import android.app.Application
import com.burrowsapps.example.gif.App
import com.burrowsapps.example.gif.di.module.ActivityBuilderModule
import com.burrowsapps.example.gif.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilderModule::class
  ]
)
interface AppComponent : AndroidInjector<App> {
  @Component.Builder
  interface Builder {
    @BindsInstance fun application(application: Application): Builder

    fun build(): AppComponent
  }
}
