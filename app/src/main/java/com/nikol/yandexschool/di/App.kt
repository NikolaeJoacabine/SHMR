package com.nikol.yandexschool.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import com.nikol.data.workManager.AppWorkerFactory
import com.nikol.data.workManager.SyncWorkerScheduler
import com.nikol.settings.SettingsManager
import javax.inject.Inject


class App : Application(), Configuration.Provider {
    lateinit var appComponent: AppComponent
    @Inject
    lateinit var workerFactory: AppWorkerFactory
    @Inject
    lateinit var settingsManager: SettingsManager

    private val config by lazy {
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.VERBOSE)
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)

        SyncWorkerScheduler.scheduleSync(this, settingsManager)
    }

    override val workManagerConfiguration: Configuration
        get() = config
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }
