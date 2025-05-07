package dev.abd3lraouf.learn.decompose

import android.app.Application
import dev.abd3lraouf.learn.decompose.di.appModule
import dev.abd3lraouf.learn.decompose.di.componentsModule
import dev.abd3lraouf.learn.decompose.di.dataModule
import dev.abd3lraouf.learn.decompose.di.domainModule
import dev.abd3lraouf.learn.decompose.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DecomposeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@DecomposeApp)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule, 
                    presentationModule,
                    componentsModule
                )
            )
        }
    }
}