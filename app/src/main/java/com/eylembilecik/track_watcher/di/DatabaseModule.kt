package com.eylembilecik.track_watcher.di

import android.content.Context
import androidx.room.Room
import com.eylembilecik.track_watcher.data.local.AppDatabase
import com.eylembilecik.track_watcher.data.local.WatchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "track_watcher_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWatchDao(db: AppDatabase): WatchDao = db.watchDao()
}
