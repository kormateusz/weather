package pl.kormateusz.weather

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.kormateusz.weather.data.database.AppDatabase
import pl.kormateusz.weather.data.database.daos.LocationDao
import pl.kormateusz.weather.data.models.entities.LocationEntity

@RunWith(AndroidJUnit4::class)
class LocationDaoTests {

    private lateinit var appDatabase: AppDatabase

    private val locationDao: LocationDao
        get() = appDatabase.getLocationDao()

    @Before
    fun setupDataBase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @Test
    fun testInsertLocation() = runBlocking {
        // Given
        val location = LocationEntity(id = "1", name = "Warszawa")

        // When
        val insertedId = locationDao.insert(location)

        // Then
        assertEquals(1L, insertedId)
    }

    @Test
    fun testGetLocations() = runBlocking {
        // Given
        val location1 = LocationEntity(id = "1", name = "Kraków")
        val location2 = LocationEntity(id = "2", name = "Gdańsk")
        locationDao.insert(location1)
        locationDao.insert(location2)

        // When
        val locations = locationDao.get()

        // Then
        assertEquals(2, locations.size)
        assertEquals("Gdańsk", locations[0].name)
        assertEquals("Kraków", locations[1].name)
    }

    @After
    fun closeDataBase() {
        appDatabase.close()
    }
}