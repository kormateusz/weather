package pl.kormateusz.weather.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.kormateusz.weather.BuildConfig
import pl.kormateusz.weather.data.database.daos.LocationDao
import pl.kormateusz.weather.data.models.entities.LocationEntity

private const val DATABASE_NAME = "${BuildConfig.APPLICATION_ID}.database"

@Database(
    entities = [
        LocationEntity::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
}

fun getAppDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()