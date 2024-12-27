package com.example.budgetbuddy.repositoriesTests

import com.example.budgetbuddy.di.FakePlacesDao
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.PlaceCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FakePlacesDaoTest {

    private lateinit var placesDao: FakePlacesDao

    @Before
    fun setup() {
        placesDao = FakePlacesDao()
    }

    @Test
    fun testInsertPlace() = runBlocking {
        val place = Place(
            userId = "user1",
            name = "Kavarna",
            address = "Brno",
            category = PlaceCategory.CLOTHING,
            imageName = null,
            latitude = 51.2,
            longitude = 16.2
        )

        val insertedId = placesDao.insert(place)

        assertEquals(0L, insertedId)

        val fetchedPlace = placesDao.getAllByUser( "user1").first()
        assertNotNull(fetchedPlace)
        assertEquals(place.id, fetchedPlace[0].id)
    }

    @Test
    fun testUpdatePlace() = runBlocking {
        val place = Place(
            userId = "user1",
            name = "Kavarna",
            address = "Brno",
            category = PlaceCategory.CLOTHING,
            imageName = null,
            latitude = 51.2,
            longitude = 16.2
        )

        placesDao.insert(place)

        val updatedPlace = place.copy(address = "Praha")
        placesDao.update(updatedPlace)

        val fetchedPlace = placesDao.getAllByUser("user1").first()
        assertEquals("Praha", fetchedPlace[0].address, "Praha")
    }

    @Test
    fun testDeletePlace() = runBlocking {
        val place = Place(
            userId = "user1",
            name = "Kavarna",
            address = "Brno",
            category = PlaceCategory.CLOTHING,
            imageName = null,
            latitude = 51.2,
            longitude = 16.2
        )

        placesDao.insert(place)
        placesDao.delete(place)

        val fetchedPlace = placesDao.getAllByUser("user1").firstOrNull()
        assertEquals(fetchedPlace?.size, 0)
    }

    @Test
    fun testGetAllPlacesByUser() = runBlocking {
        val place1 = Place(
            userId = "user1",
            name = "Kavarna",
            address = "Brno",
            category = PlaceCategory.CLOTHING,
            imageName = null,
            latitude = 51.2,
            longitude = 16.2
        )

        val place2 = Place(
            userId = "user1",
            name = "Kavarna",
            address = "Brno",
            category = PlaceCategory.CLOTHING,
            imageName = null,
            latitude = 51.2,
            longitude = 16.2
        )

        placesDao.insert(place1)
        placesDao.insert(place2)

        val places = placesDao.getAllByUser("user1").first()
        assertEquals(2, places.size)
        assertTrue(places.contains(place1))
        assertTrue(places.contains(place2))
    }
}
