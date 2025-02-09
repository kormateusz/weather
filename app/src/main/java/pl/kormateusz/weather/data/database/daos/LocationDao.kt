package pl.kormateusz.weather.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.kormateusz.weather.data.models.entities.LocationEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: LocationEntity): Long

    @Query("SELECT * FROM locations ORDER BY name")
    suspend fun get(): List<LocationEntity>
}