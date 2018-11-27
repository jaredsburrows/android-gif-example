package burrows.apps.example.gif.giflist

import burrows.apps.example.gif.di.module.NetModule
import burrows.apps.example.gif.di.module.RiffsyModule
import dagger.Module

@Module(includes = [NetModule::class, RiffsyModule::class])
abstract class GifModule
