package com.eylembilecik.track_watcher.di

import android.content.Context
import androidx.room.Room
import com.eylembilecik.track_watcher.data.local.AppDatabase
import com.eylembilecik.track_watcher.data.local.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    fun provideFavoriteMovieDao(db: AppDatabase): FavoriteMovieDao {
        return db.favoriteMovieDao()
    }
}
