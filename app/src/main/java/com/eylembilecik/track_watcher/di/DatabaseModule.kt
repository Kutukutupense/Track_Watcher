package com.eylembilecik.track_watcher.di

import android.content.Context
import androidx.room.Room
import com.eylembilecik.track_watcher.data.local.AppDatabase
import com.eylembilecik.track_watcher.data.local.FavoriteMovieDao
import com.eylembilecik.track_watcher.data.local.MIGRATION_1_TO_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .addMigrations(MIGRATION_1_TO_2)
            .build()

    @Provides
    fun provideFavoriteMovieDao(db: AppDatabase): FavoriteMovieDao = db.favoriteMovieDao()
}
