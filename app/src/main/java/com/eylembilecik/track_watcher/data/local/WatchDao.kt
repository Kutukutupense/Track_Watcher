package com.eylembilecik.track_watcher.data.local

import androidx.room.*
import com.eylembilecik.track_watcher.data.model.WatchItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: WatchItem)

    @Delete
    suspend fun deleteItem(item: WatchItem)

    @Query("SELECT * FROM watch_items")
    fun getAllItems(): Flow<List<WatchItem>>
}
